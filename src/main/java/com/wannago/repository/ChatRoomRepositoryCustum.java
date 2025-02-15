package com.wannago.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.wannago.dto.ResponseChatRoomDTO;

@Repository
public interface ChatRoomRepositoryCustum {
    List<ResponseChatRoomDTO> findByUsIdx(Integer usIdx);
}
