package com.wannago.repository;
import com.wannago.entity.LocationSi;
import com.wannago.entity.LocationSiId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.wannago.entity.LocationSiId;

public interface LocationSiRepository extends JpaRepository<LocationSi, LocationSiId> {

    // location_si 테이블에서 모든 데이터 조회
    List<LocationSi> findAll();

    // location_si 테이블에서 ldIdx를 포함하는 데이터들을 조회
    List<LocationSi> findByLdIdx(int ldIdx);
}
