package com.wannago.repository;

import com.wannago.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalTime;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // trIdx를 포함하는 데이터들을 조회 scDate 오름차순 , sc_start_time 내림차순
    @Query("SELECT s FROM Schedule s WHERE s.trIdx = :trIdx ORDER BY s.scDate ASC, s.scStartTime DESC")
    List<Schedule> findByTrIdx(@Param("trIdx") int trIdx);

    // scIdx를 포함하는 데이터들을 삭제
    @Query("DELETE FROM Schedule s WHERE s.scIdx = :scIdx")
    void deleteByScIdx(@Param("scIdx") int scIdx);

    // scIdx를 포함하는 데이터의 scStartTime , scEndTime 수정
    @Transactional
    @Modifying
    @Query("UPDATE Schedule s SET s.scStartTime = :scStartTime, s.scEndTime = :scEndTime WHERE s.scIdx = :scIdx")
    void updateSchedule(@Param("scIdx") int scIdx, @Param("scStartTime") LocalTime scStartTime,
            @Param("scEndTime") LocalTime scEndTime);
}
