package com.wannago.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wannago.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer>, ChatRoomRepositoryCustum {
    ChatRoom findByCrIdx(Integer crIdx);
}
