package com.wannago.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wannago.dto.ResponseChatRoomDTO;
import com.wannago.entity.ChatRoom;
import com.wannago.entity.QChatMember;
import com.wannago.entity.QChatRoom;
import com.wannago.entity.QMessage;
import com.wannago.entity.QUser;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryCustumImpl    implements ChatRoomRepositoryCustum {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ResponseChatRoomDTO> findByUsIdx(Integer usIdx) {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChatMember chatMember = QChatMember.chatMember;
        QUser user = QUser.user;
        QMessage message = QMessage.message;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(chatMember.usIdx.eq(usIdx));

        List<ResponseChatRoomDTO> result = queryFactory
            .select(Projections.constructor(ResponseChatRoomDTO.class,
                chatMember.crIdx,
                user.usName,
                user.usProfile,
                user.usState,
                message.msgContent,
                message.msgCreatedAt))
            .from(chatMember)
            .join(chatRoom).on(chatMember.crIdx.eq(chatRoom.crIdx))
            .join(user).on(chatMember.usIdx.ne(usIdx).and(chatMember.usIdx.eq(user.usIdx)))
            .leftJoin(message).on(message.crIdx.eq(chatRoom.crIdx))
            .where(builder)
            .groupBy(chatMember.crIdx)
            .orderBy(message.msgCreatedAt.desc())
            .fetch();

        // 전체 카운트 쿼리
        Long total = queryFactory
            .select(chatMember.count())
            .from(chatMember)
            .where(builder)
            .fetchOne();

        return result;
    }
}
