package com.wannago.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wannago.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardRepositoryCustom{
    List<Board> findByBoState(Integer boState);
    List<Board> findByBoStateAndBoIdx(Integer boState, Integer boIdx);
    Page<Board> findByBoState(Integer boState, Pageable pageable);
    Optional<Board> findByBoIdx(Integer boIdx);
}
