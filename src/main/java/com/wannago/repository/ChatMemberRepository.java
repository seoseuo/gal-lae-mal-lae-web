package com.wannago.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wannago.entity.ChatMember;
import com.wannago.entity.ChatMemberId;

public interface ChatMemberRepository extends JpaRepository<ChatMember, ChatMemberId> {
    List<ChatMember> findByCrIdx(Integer crIdx);
    List<ChatMember> findByUsIdx(Integer usIdx);

    @Query("SELECT cm FROM ChatMember cm WHERE cm.usIdx = :usIdx AND cm.crIdx IN (SELECT cm.crIdx FROM ChatMember cm WHERE cm.usIdx = :usIdx2)")
    Optional<ChatMember> findByUsIdxAndUsIdx2(Integer usIdx, Integer usIdx2);
}
