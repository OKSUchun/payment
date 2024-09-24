package com.firffin.payment.global.security;

import com.firffin.payment.global.error.exception.CustomException;
import com.firffin.payment.global.error.exception.ErrorCode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomPasswordEncoder implements PasswordEncoder {

    private static final Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);

    private final String SHA_512 = "SHA-512";

    @Override
    public String hashPassword(String rawPassword) {
        return hashPasswordWithMessageDigest(rawPassword, SHA_512);
    }

    @Override
    public Boolean isMatched(String rawPassword, String hashedPassword) {
        String hashedRawpassword = hashPasswordWithMessageDigest(rawPassword, SHA_512);

        return hashedRawpassword.equals(hashedPassword);
    }

    private String hashPasswordWithMessageDigest(String rawPassword, String algorithm)
        throws CustomException {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            digest.update(rawPassword.getBytes(StandardCharsets.UTF_8));
            byte[] hashedBytes = digest.digest();
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("해싱 알고리즘 확인이 어렵습니다: " + algorithm, e);
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }
}