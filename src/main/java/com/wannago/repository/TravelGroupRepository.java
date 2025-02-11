package com.wannago.repository;

import com.wannago.entity.TravelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TravelGroupRepository extends JpaRepository<TravelGroup, Integer> {

    @Override
    <S extends TravelGroup> S save(S entity);
        
    
    // 특정 모임 조회 - grIdx로 찾기
    Optional<TravelGroup> findByGrIdx(int grIdx);
    
    // 여러 그룹 ID로 그룹들 조회 - 추가
    List<TravelGroup> findAllByGrIdxIn(List<Integer> grIdxList);
}
