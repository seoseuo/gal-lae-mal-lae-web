package com.wannago.repository;

import com.wannago.entity.TravelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TravelGroupRepository extends JpaRepository<TravelGroup, Integer> {

    @Override
    <S extends TravelGroup> S save(S entity);

    // 모임 목록 조회
    List<TravelGroup> findAll();
}
