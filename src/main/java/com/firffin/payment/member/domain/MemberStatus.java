package com.firffin.payment.member.domain;

public enum MemberStatus {
    /**
     * 부모회원 : 회원가입 O
     * 자녀회원 : 부모 등록 O && 본인 회원가입 O
     */
    ACTIVE,
    /**
     * 부모회원 : 해당 없음
     * 자녀회원 : 부모 등록 O && 본인 회원가입 X
     */
    INACTIVE,
    /**
     * 부모회원, 자녀회원 : 휴면 계정
     */
    DORMANT
}
