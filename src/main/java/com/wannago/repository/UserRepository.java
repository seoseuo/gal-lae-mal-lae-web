package com.wannago.repository;

import com.wannago.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    // usIdx를 통해 User 정보를 가져오는 메서드
    // 내 정보 조회 시 사용
    Optional<User> findByUsIdx(int usIdx);

    // usIdx 목록을 통해 User 목록을 가져오는 메서드
    List<User> findByUsIdxInAndUsState(List<Integer> usIdxs, int usState);

    // usIdx를 통해 User 이름을 수정하는 메서드
    // 이름 수정 시 사용
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.usName = :usName WHERE u.usIdx = :usIdx")
    void updateUsNameByUsIdx(@Param("usIdx") int usIdx, @Param("usName") String usName);

    // usName을 통해 User 정보를 가져오는 메서드
    // 중복 체크 시 사용
    Optional<User> findByUsName(String usName);

    // usEmail을 통해 User 정보를 가져오는 메서드
    // 로그인 시 사용
    Optional<User> findByUsEmail(String usEmail);

    // usIdx를 통해 User 비밀번호를 수정하는 메서드
    // 비밀번호 변경 시 사용
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.usPw = :usPw WHERE u.usIdx = :usIdx")
    void updateUsPwByUsIdx(@Param("usIdx") int usIdx, @Param("usPw") String usPw);

    // usIdx를 통해 User 프로필 이미지를 수정하는 메서드
    // 프로필 이미지 수정 시 사용
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.usProfile = :usProfile WHERE u.usIdx = :usIdx")
    void updateUsProfileByUsIdx(@Param("usIdx") int usIdx, @Param("usProfile") String usProfile);


    // usEmail을 통해 User 정보를 가져오는 메서드
    // 초대를 위한 유저 이메일 검색 시 사용
    // Member 테이블에서 grIdx에 포함된 usIdx는 제외
    @Query("SELECT u FROM User u WHERE u.usEmail LIKE %:usEmail% AND u.usIdx NOT IN (SELECT m.usIdx FROM Member m WHERE m.grIdx = :grIdx)")
    List<User> findByUsEmailContaining(@Param("usEmail") String usEmail, @Param("grIdx") int grIdx);

}
