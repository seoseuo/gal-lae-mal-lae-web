package com.wannago.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wannago.dto.ResponseChatRoomDTO;
import com.wannago.entity.ChatRoom;
import com.wannago.entity.QChatMember;
import com.wannago.entity.QChatRoom;
import com.wannago.entity.QMessage;
import com.wannago.entity.QUser;
import com.wannago.entity.Message;

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
        QMessage message2 = new QMessage("message2");

        return queryFactory
            .select(Projections.constructor(ResponseChatRoomDTO.class,
                user.usIdx,
                user.usName, 
                user.usProfile,
                message.msgContent,
                message.msgCreatedAt))
            .from(chatMember)
            .join(user).on(user.usIdx.eq(chatMember.usIdx).and(user.usIdx.ne(usIdx)))
            .leftJoin(message).on(message.crIdx.eq(chatMember.crIdx)
                .and(message.msgCreatedAt.eq(
                    JPAExpressions
                        .select(message2.msgCreatedAt.max())
                        .from(message2)
                        .where(message2.crIdx.eq(chatMember.crIdx))
                )))
            .where(chatMember.crIdx.in(
                JPAExpressions
                    .select(chatMember.crIdx)
                    .from(chatMember)
                    .where(chatMember.usIdx.eq(usIdx))
                    .distinct()
            ))
            .orderBy(message.msgCreatedAt.desc())
            .fetch();
    }
}
