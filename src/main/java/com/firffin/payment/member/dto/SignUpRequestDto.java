package com.firffin.payment.member.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String username;
    private String password;
    private String phoneNumber;
    private String role;

}
