package com.firffin.payment.global.config;

import com.firffin.payment.global.security.CustomPasswordEncoder;
import com.firffin.payment.global.security.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

}