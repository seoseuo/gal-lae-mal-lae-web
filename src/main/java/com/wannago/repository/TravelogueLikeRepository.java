package com.wannago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wannago.entity.TravelogueLike;
public interface TravelogueLikeRepository extends JpaRepository<TravelogueLike, Integer> {
    // 여행록 좋아요 리스트 가져오기
    List<TravelogueLike> findByTlIdxIn(List<Integer> tlIdxList);

    // tlIdx와 usIdx를 포함하는 데이터 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM TravelogueLike tl WHERE tl.tlIdx = :tlIdx AND tl.usIdx = :usIdx")
    void deleteByTlIdxAndUsIdx(@Param("tlIdx") int tlIdx, @Param("usIdx") int usIdx);

    // tlIdx와 usIdx를 포함하는 데이터 조회
    TravelogueLike findByTlIdxAndUsIdx(@Param("tlIdx") int tlIdx, @Param("usIdx") int usIdx);
    
}
