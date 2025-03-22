package com.wannago.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.wannago.dto.BoardDTO;
import com.wannago.dto.BoardSearchDTO;
import com.wannago.entity.Board;
import com.wannago.entity.QBoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    
    @Override
    public Page<Board> findByFilter(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        QBoard board = QBoard.board;
        BooleanBuilder builder = new BooleanBuilder();
        OrderSpecifier<?> sort = board.boIdx.desc();
        builder.and(board.boState.eq(1));
        if(boardSearchDTO.getSearch() != null){
            builder.and(board.boTitle.contains(boardSearchDTO.getSearch()));
        }
        if(boardSearchDTO.getLdIdx() != null){
            builder.and(board.ldIdx.eq(boardSearchDTO.getLdIdx()));
        }
        if(boardSearchDTO.getSort() != null){
            if(boardSearchDTO.getSort().equals("lastest")){
                sort = board.boIdx.desc();
            }else if(boardSearchDTO.getSort().equals("oldest")){
                sort = board.boIdx.asc();
            }else if(boardSearchDTO.getSort().equals("like")){
                sort = board.boLike.desc();
            }
        }
        
        List<Board> query = queryFactory
            .selectFrom(board)
            .where(builder)
            .orderBy(sort)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
            
        long total = queryFactory.select(board.count())
            .from(board)
            .where(builder)
            .fetchOne();
        return new PageImpl<>(query, pageable, total);
    }
}
