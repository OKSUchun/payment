package com.firffin.payment.member.converter;

import com.firffin.payment.global.security.PasswordEncoder;
import com.firffin.payment.member.domain.Member;
import com.firffin.payment.member.dto.SignUpRequestDto;
import com.firffin.payment.member.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpDtoConverter {

    private final PasswordEncoder passwordEncoder;

    public Member convertSignUpRequestToMember(SignUpRequestDto signUpRequest) {

        return Member.createParentMember(signUpRequest.getPhoneNumber(),
            signUpRequest.getUsername(),
            passwordEncoder.hashPassword(signUpRequest.getPassword()));
    }

    public SignUpResponseDto convertUserToSignUpResponse(Member member) {
        return new SignUpResponseDto(member.getUsername(), member.getId());
    }
}
