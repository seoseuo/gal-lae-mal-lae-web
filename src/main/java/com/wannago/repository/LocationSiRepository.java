package com.wannago.repository;
import com.wannago.entity.LocationSi;
import com.wannago.entity.LocationSiId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.wannago.entity.LocationSiId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface LocationSiRepository extends JpaRepository<LocationSi, LocationSiId> {

    // location_si 테이블에서 모든 데이터 조회
    List<LocationSi> findAll();

    // location_si 테이블에서 ldIdx를 포함하는 데이터들을 조회
    List<LocationSi> findByLdIdx(int ldIdx);

    // lsIdx 값을 받아서 해당하는 lsName을 가져오는 함수
    @Query("SELECT l.lsName FROM LocationSi l WHERE l.ldIdx = :ldIdx AND l.lsIdx = :lsIdx")
    Optional<String> findLocationNameByLsIdx(@Param("ldIdx") int ldIdx, @Param("lsIdx") int lsIdx);
}
