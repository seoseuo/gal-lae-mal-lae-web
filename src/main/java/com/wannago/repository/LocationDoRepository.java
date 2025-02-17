package com.wannago.repository;

import com.wannago.entity.LocationDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface LocationDoRepository extends JpaRepository<LocationDo, Integer> {

    // location_do 테이블에서 모든 데이터 조회
    List<LocationDo> findAll();


}
