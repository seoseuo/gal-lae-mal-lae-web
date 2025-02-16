package com.wannago.repository;

import com.wannago.entity.Travelogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TravelogueRepository extends JpaRepository<Travelogue, Integer> {

    // trIdx를 포함하는 데이터들을 조회 AND tlState = 1
    @Query("SELECT t FROM Travelogue t WHERE t.trIdx = :trIdx AND t.tlState = 1")
    List<Travelogue> findByTrIdx(@Param("trIdx") int trIdx);
}
