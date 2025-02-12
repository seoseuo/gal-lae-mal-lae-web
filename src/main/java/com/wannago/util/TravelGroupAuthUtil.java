package com.wannago.util;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.wannago.repository.MemberRepository;
import com.wannago.entity.Member;
import com.wannago.util.security.SecurityUtil;
import com.wannago.dto.UserDTO;

@Component
public class TravelGroupAuthUtil {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SecurityUtil securityUtil;

    // 모임 회원 권한 조회
    public boolean checkMemberAuth(int grIdx) {
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        Member member = memberRepository.findByGrIdxAndUsIdx(grIdx, userDTO.getUsIdx());
        if (member == null) {
            return false;
        }
        return true;
    }

    // 모임 회장 권한 조회
    public boolean checkAdminAuth(int grIdx) {
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        Member member = memberRepository.findByGrIdxAndUsIdx(grIdx, userDTO.getUsIdx());        
        if (member.getMeRole() != Member.MemberRole.ADMIN) {
            return false;
        }
        return true;
    }
}