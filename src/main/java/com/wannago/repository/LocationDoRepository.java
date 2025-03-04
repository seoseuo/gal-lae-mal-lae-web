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

    // 매개변수로 받은 IntegerList를 in 하여 해당하는 ldName을 가져오는 함수
    @Query("SELECT DISTINCT l.ldName FROM LocationDo l WHERE l.ldIdx IN :ldIdxList")
    List<String> findLocationNamesByLdIdxList(@Param("ldIdxList") List<Integer> ldIdxList);

    // ldIdx 값을 받아서 해당하는 ldName을 가져오는 함수
    @Query("SELECT l.ldName FROM LocationDo l WHERE l.ldIdx = :ldIdx")
    Optional<String> findLocationNameByLdIdx(@Param("ldIdx") int ldIdx);

}
