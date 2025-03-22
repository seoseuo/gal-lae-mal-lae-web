package com.wannago.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wannago.dto.BoardSearchDTO;
import com.wannago.entity.Board;

public interface BoardRepositoryCustom {
    Page<Board> findByFilter(BoardSearchDTO boardSearchDTO, Pageable pageable);
}
