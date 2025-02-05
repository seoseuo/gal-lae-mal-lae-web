package com.wannago.repository;

import com.wannago.entity.Travelogue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelogueRepository extends JpaRepository<Travelogue, Integer> {
}
