package com.wannago.repository;

import com.wannago.entity.Follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.wannago.entity.User;


public interface FollowRepository extends JpaRepository<Follow, Integer> {
    // usIdx를 통해 User가 팔로우 하는 목록을 가져오는 메서드
    // 팔로우 목록 조회 시 사용
    @Query("SELECT f.followee FROM Follow f WHERE f.follower = :usIdx")
    List<Integer> getFollowingListByUsIdx(@Param("usIdx") int usIdx);

    // usIdx를 통해 User가 팔로우 받는 목록을 가져오는 메서드
    // 팔로우 목록 조회 시 사용
    @Query("SELECT f.follower FROM Follow f WHERE f.followee = :usIdx")
    List<Integer> getFollowerListByUsIdx(@Param("usIdx") int usIdx);
}
