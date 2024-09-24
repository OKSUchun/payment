package com.firffin.payment.member.service;

import com.firffin.payment.member.dto.SignUpRequestDto;
import com.firffin.payment.member.dto.SignUpResponseDto;

/**
 * {@link MemberService} 중 SignUp과 관련된 interface 정의
 */
public interface MemberSignUpService {

    public SignUpResponseDto trySignUpMember(SignUpRequestDto signUpRequest);
}
