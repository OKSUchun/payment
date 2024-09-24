package com.firffin.payment.member.repository;

import com.firffin.payment.member.domain.Member;
import com.firffin.payment.member.domain.MemberRole;
import com.firffin.payment.member.domain.MemberStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    // 1. 전화번호와 사용자 이름으로 자녀 찾기
    Optional<Member> findByPhoneNumberAndUsernameAndMemberRoleAndMemberStatus(
        String phoneNumber,
        String username,
        MemberRole memberRole,
        MemberStatus memberStatus
    );

}
