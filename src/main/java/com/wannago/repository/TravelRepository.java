package com.wannago.repository;

import com.wannago.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TravelRepository extends JpaRepository<Travel, Integer> {
    
    // grIdx를 포함하는 데이터들을 조회    
    List<Travel> findByGrIdx(int grIdx);
}
