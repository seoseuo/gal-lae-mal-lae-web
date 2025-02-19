package com.wannago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wannago.entity.TravelogueLike;

public interface TravelogueLikeRepository extends JpaRepository<TravelogueLike, Integer> {
    // 여행록 좋아요 리스트 가져오기
    List<TravelogueLike> findByTrIdx(int trIdx);
}
