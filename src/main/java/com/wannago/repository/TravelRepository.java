package com.wannago.repository;

import com.wannago.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface TravelRepository extends JpaRepository<Travel, Integer> {

    // grIdx를 포함하는 데이터들을 조회
    // state가 1인 경우만 조회
    @Query("SELECT t FROM Travel t WHERE t.grIdx = :grIdx AND t.trState = 1")
    List<Travel> findByGrIdx(@Param("grIdx") int grIdx);

    // trIdx를 포함하는 데이터들을 조회 AND trState = 1
    @Query("SELECT t FROM Travel t WHERE t.trIdx = :trIdx AND t.trState = 1")
    Optional<Travel> findByTrIdx(@Param("trIdx") int trIdx);

    // 여행지 삭제 trIdx를 포함하는 trState를 0으로 변경
    @Modifying
    @Transactional
    @Query("UPDATE Travel t SET t.trState = 0 WHERE t.trIdx = :trIdx")
    void updateTrStateByTrIdx(@Param("trIdx") int trIdx);

    
    @Query("SELECT t.ldIdx FROM Travel t WHERE t.grIdx = :grIdx AND t.trState = 1")
    List<Integer> findLdIdxByGrIdx(@Param("grIdx") int grIdx);

}
