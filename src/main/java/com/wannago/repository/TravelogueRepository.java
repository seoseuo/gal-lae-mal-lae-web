package com.wannago.repository;

import com.wannago.entity.Travelogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TravelogueRepository extends JpaRepository<Travelogue, Integer> {

    // trIdx를 포함하는 데이터들을 조회 AND tlState = 1
    @Query("SELECT t FROM Travelogue t WHERE t.trIdx = :trIdx AND t.tlState = 1")
    List<Travelogue> findByTrIdx(@Param("trIdx") int trIdx);

    // 여행록 수정
    // 변경된 사항만 수정
    // 매개변수는 객체
    // tlIdx와 tlState, usIdx는 변경하지 않음
    @Modifying
    @Transactional    
    @Query("UPDATE Travelogue t SET t.tlTitle = :tlTitle, t.tlContent = :tlContent, t.tlImage = :tlImage, t.tlPublic = :tlPublic WHERE t.tlIdx = :tlIdx")
    void updateTravelogue(@Param("tlIdx") int tlIdx,
            @Param("tlTitle") String tlTitle,
            @Param("tlContent") String tlContent,
            @Param("tlImage") String tlImage,
            @Param("tlPublic") int tlPublic);

    // 여행록 삭제
    // tlState를 0으로 변경
    @Modifying
    @Transactional
    @Query("UPDATE Travelogue t SET t.tlState = 0 WHERE t.tlIdx = :tlIdx")
    void deleteTravelogue(@Param("tlIdx") int tlIdx);

    // 여행지 삭제 시 trIdx를 포함하는 tlState를 0으로 변경
    @Modifying
    @Transactional
    @Query("UPDATE Travelogue t SET t.tlState = 0 WHERE t.trIdx = :trIdx")
    void updateTlStateByTrIdx(@Param("trIdx") int trIdx);

    // tlState = 1 && tlPublic = 1 && 조건으로 페이징 처리
    // tlTitle like %keyword% or tlContent like %keyword%
    @Query("SELECT t FROM Travelogue t WHERE t.tlState = 1 AND t.tlPublic = 1 AND (t.tlTitle LIKE %:keyword% OR t.tlContent LIKE %:keyword%)")
    Page<Travelogue> findByTlStateAndTlPublic(@Param("tlState") int tlState, @Param("tlPublic") int tlPublic, Pageable pageable, @Param("keyword") String keyword);

    // trIdx를 포함포함하는 모든 데이터들의 tlImage 목록
    @Query("SELECT t.tlImage FROM Travelogue t WHERE t.trIdx = :trIdx")
    List<String> findTlImageByTrIdx(@Param("trIdx") int trIdx);

    // grIdx를 포함하는 travel 테이블의 trIdx를 추출한 후 travelogue 테이블에서 trIdx과 usIdx를 포함하는 모든 데이터들의 tlState를 0으로 변경
    @Modifying
    @Transactional
    @Query("UPDATE Travelogue t SET t.tlState = 0 WHERE t.trIdx IN (SELECT trIdx FROM Travel t WHERE t.grIdx = :grIdx) AND t.usIdx = :usIdx")
    void updateTlStateByGrIdxAndUsIdx(@Param("grIdx") int grIdx, @Param("usIdx") int usIdx);
}
