package com.firffin.payment.global.security;

public interface PasswordEncoder {

    String hashPassword(String rawPassword);

    Boolean isMatched(String rawPassword, String hashedPassword);
}
