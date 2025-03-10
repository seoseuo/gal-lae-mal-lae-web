package com.wannago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wannago.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findByBoState(Integer boState);
    List<Board> findByBoStateAndBoIdx(Integer boState, Integer boIdx);
}
