package com.wannago.repository;

import com.wannago.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // usIdx를 통해 User 정보를 가져오는 메서드
    Optional<User> findByUsIdx(int usIdx);
}
