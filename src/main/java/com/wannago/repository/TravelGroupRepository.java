package com.wannago.repository;

import com.wannago.entity.TravelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, Integer> {

    @Override
    <S extends TravelGroup> S save(S entity);

    // 모임 목록 조회
    List<TravelGroup> findAll();

    // 특정 모임 조회
    Optional<TravelGroup> findByGrIdx(int grIdx);
}
