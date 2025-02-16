package com.wannago.repository;

import com.wannago.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {    

    // trIdx를 포함하는 데이터들을 조회 scDate 오름차순 , sc_start_time 내림차순    
    @Query("SELECT s FROM Schedule s WHERE s.trIdx = :trIdx ORDER BY s.scDate ASC, s.scStartTime DESC")
    List<Schedule> findByTrIdx(@Param("trIdx") int trIdx);
}
