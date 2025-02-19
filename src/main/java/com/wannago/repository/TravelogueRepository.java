package com.wannago.repository;

import com.wannago.entity.Travelogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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

}
