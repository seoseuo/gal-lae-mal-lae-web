package com.wannago.repository;

import com.wannago.entity.Member;
import com.wannago.entity.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, MemberId> {

    @Override
    // 모임 생성 시 회원 추가
    // 회장 추가 및 회원 추가에 사용
    <S extends Member> S save(S entity);

    // 회원 번호로 모임 목록 가져오기
    List<Member> findByUsIdx(int usIdx);

    // 모임 번호 리스트 가져오기
    @Query("SELECT m.grIdx FROM Member m WHERE m.usIdx = :usIdx")
    List<Integer> findGrIdxByUsIdx(@Param("usIdx") int usIdx);

    // 모임 회원 조회
    // 모임 회원 접근 권한 조회 시 사용
    Member findByGrIdxAndUsIdx(int grIdx, int usIdx);

    // 모임 번호로 모임 회원 목록 가져오기
    List<Member> findByGrIdx(int grIdx);

    // 모임 번호로 모임 회원 인원 수 가져오기
    @Query("SELECT COUNT(m) FROM Member m WHERE m.grIdx = :grIdx")
    int countByGrIdx(@Param("grIdx") int grIdx);

    // 특정 모임 권한 변경
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.meRole = :newRole WHERE m.grIdx = :grIdx AND m.usIdx = :usIdx")
    void updateMeRoleByGrIdxAndUsIdx(@Param("grIdx") int grIdx, @Param("usIdx") int usIdx,
            @Param("newRole") Member.MemberRole newRole);

    // 모임 탈퇴
    @Transactional
    void deleteByGrIdxAndUsIdx(int grIdx, int usIdx);

    // 모임 삭제
    @Transactional
    void deleteByGrIdx(int grIdx);

}
