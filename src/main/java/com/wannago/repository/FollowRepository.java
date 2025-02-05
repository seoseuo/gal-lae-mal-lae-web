package com.wannago.repository;
import com.wannago.entity.Follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
}
