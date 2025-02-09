package com.wannago.repository;

import com.wannago.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // usIdx를 통해 User 정보를 가져오는 메서드
    Optional<User> findByUsIdx(int usIdx);

    // usIdx를 통해 User 정보를 수정하는 메서드
    @Modifying
    @Query("UPDATE User u SET u.usName = :usName WHERE u.usIdx = :usIdx")
    void updateUsNameByUsIdx(int usIdx, String usName);
}
