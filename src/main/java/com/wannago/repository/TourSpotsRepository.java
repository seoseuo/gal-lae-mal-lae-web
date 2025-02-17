package com.wannago.repository;
import com.wannago.entity.TourSpots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface TourSpotsRepository extends JpaRepository<TourSpots, Integer> {
    
    // ldIdx와 lsIdx를 포함하는 데이터들을 조회
    @Query("SELECT t FROM TourSpots t WHERE t.ldIdx = :ldIdx AND t.lsIdx = :lsIdx")
    List<TourSpots> findByLsIdx(@Param("ldIdx") int ldIdx, @Param("lsIdx") int lsIdx);
}
