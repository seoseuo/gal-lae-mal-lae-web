package com.wannago.repository;

import com.wannago.entity.TravelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Date;


@Repository
public interface TravelGroupRepository extends JpaRepository<TravelGroup, Integer> {

    @Override
    <S extends TravelGroup> S save(S entity);
    

    // 특정 모임 조회 - grIdx로 찾기 AND grState = 1
    @Query("SELECT t FROM TravelGroup t WHERE t.grIdx = :grIdx AND t.grState = 1")
    Optional<TravelGroup> findByGrIdx(@Param("grIdx") int grIdx);

    // 여러 그룹 ID로 그룹들 조회 - 추가
    @Query("SELECT t FROM TravelGroup t WHERE t.grIdx IN :grIdxList AND t.grState = 1")
    List<TravelGroup> findAllByGrIdxIn(@Param("grIdxList") List<Integer> grIdxList);

    // 모임 삭제
    @Transactional
    @Modifying
    @Query("UPDATE TravelGroup t SET t.grState = 0, t.grDeletedAt = :grDeletedAt WHERE t.grIdx = :grIdx")
    void updateGrStatusByGrIdx(@Param("grIdx") int grIdx, @Param("grDeletedAt") Date grDeletedAt);

    
}
