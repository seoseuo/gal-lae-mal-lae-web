package com.wannago.repository;

import com.wannago.entity.TourSpots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TourSpotsRepository extends JpaRepository<TourSpots, Integer> {

    // ldIdx와 lsIdx를 포함하는 데이터들을 조회 c1Code like , tsName like
    @Query("SELECT t FROM TourSpots t WHERE t.ldIdx = :ldIdx AND t.lsIdx = :lsIdx AND t.c1Code LIKE %:c1Code% AND t.tsName LIKE %:tsName%")
    Page<TourSpots> findByLsIdxAndC1CodeAndTsName(@Param("ldIdx") int ldIdx, @Param("lsIdx") int lsIdx,
            @Param("c1Code") String c1Code, @Param("tsName") String tsName, Pageable pageable);

    // ldIdx를 포함하는 데이터들을 조회 c1Code like , tsName like
    @Query("SELECT t FROM TourSpots t WHERE t.ldIdx = :ldIdx AND t.c1Code LIKE %:c1Code% AND t.tsName LIKE %:tsName%")
    Page<TourSpots> findByLdIdxAndC1CodeAndTsName(@Param("ldIdx") int ldIdx, @Param("c1Code") String c1Code,
            @Param("tsName") String tsName, Pageable pageable);

}
