package com.firffin.payment.member.controller;

import com.firffin.payment.member.dto.SignUpRequestDto;
import com.firffin.payment.member.dto.SignUpResponseDto;
import com.firffin.payment.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public SignUpResponseDto signUpMember(
        @RequestBody SignUpRequestDto signUpRequest) {
        return memberService.trySignUpMember(signUpRequest);
    }

}
