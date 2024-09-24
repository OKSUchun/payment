package com.firffin.payment.member.dto;

import com.firffin.payment.global.security.PasswordEncoder;
import com.firffin.payment.member.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    private String username;
    private String password;
    private String phoneNumber;
    private String role;

}
