package com.firffin.payment.member.service;

import com.firffin.payment.global.error.exception.CustomException;
import com.firffin.payment.global.error.exception.ErrorCode;
import com.firffin.payment.global.security.PasswordEncoder;
import com.firffin.payment.member.domain.Member;
import com.firffin.payment.member.domain.MemberRole;
import com.firffin.payment.member.domain.MemberStatus;
import com.firffin.payment.member.dto.SignUpRequestDto;
import com.firffin.payment.member.dto.SignUpResponseDto;
import com.firffin.payment.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberService 구현
 */
@RequiredArgsConstructor
@Service
public class MemberService implements MemberSignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     * 부모와 자녀로 구분해 진행한다.
     * 자녀회원은 반드시 부모 회원이 미리 등록한 경우만 회원 가입할 수 있다.
     *
     * @param signUpRequest 회원 가입 정보
     */
    @Override
    @Transactional
    public SignUpResponseDto trySignUpMember(SignUpRequestDto signUpRequest) {
        if (signUpRequest.getRole() == null) {
            throw new CustomException(ErrorCode.INVALID_ROLE);
        }

        // 부모인 경우
        if (signUpRequest.getRole().equals(MemberRole.PARENT.toString())) {
            // 중복 여부 확인
            return handleParentSignUp(signUpRequest);
        }

        // 아이인 경우
        if (signUpRequest.getRole().equals(MemberRole.CHILD.toString())) {
            return handleChildSignUp(signUpRequest);
        }
        throw new CustomException(ErrorCode.INVALID_ROLE);
    }

    private SignUpResponseDto handleChildSignUp(SignUpRequestDto signUpRequest) {
        // 부모가 미리 등록한 자녀 유저가 있는지 확인합니다.
        Member member = findInactiveChildUser(signUpRequest);
        // TODO: 자녀 회원의 부모가 존재하는지 확인합니다.
        member.updateChildMember(passwordEncoder.hashPassword(signUpRequest.getPassword()));
        return new SignUpResponseDto(member.getUsername(), member.getId());
    }

    private SignUpResponseDto handleParentSignUp(SignUpRequestDto signUpRequest) {
        validateSignUser(signUpRequest);
        Member member = Member.createParentMember(signUpRequest.getPhoneNumber(), signUpRequest.getUsername(), passwordEncoder.hashPassword(signUpRequest.getPassword()));
        memberRepository.save(member);
        return new SignUpResponseDto(member.getUsername(), member.getId());
    }

    private void validateSignUser(SignUpRequestDto signUpRequest) {
        if (memberRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            throw new CustomException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }
    }

    private Member findInactiveChildUser(SignUpRequestDto signUpRequest) {
        // 부모회원이 자녀 회원을 등록할 때, 자녀회원의 핸드폰 번호, 이름을 기입하여 생성한다.
        return memberRepository.findByPhoneNumberAndUsernameAndMemberRoleAndMemberStatus(
                signUpRequest.getPhoneNumber(), signUpRequest.getUsername(),
                MemberRole.CHILD, MemberStatus.INACTIVE)
            .orElseThrow(() -> new CustomException(ErrorCode.UNREGISTERED_CHILD));
    }
}