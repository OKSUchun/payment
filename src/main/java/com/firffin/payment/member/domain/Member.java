package com.firffin.payment.member.domain;

import com.firffin.payment.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    /**
     * 부모 유저 생성
     *
     * @param phoneNumber 핸드폰번호
     * @param username    이름
     * @param password    비밀번호
     * @return {@link Member} 객체
     */
    public static Member createParentMember(String phoneNumber, String username, String password) {
        return Member.builder().phoneNumber(phoneNumber).username(username).password(password)
            .memberRole(MemberRole.PARENT).memberStatus(MemberStatus.ACTIVE).build();
    }

    /**
     * 부모 유저의 자녀 회원 생성
     *
     * @param phoneNumber 자녀 핸드폰번호
     * @param username    자녀 이름
     */
    public static Member createEmptyChild(String phoneNumber, String username) {
        return Member.builder().phoneNumber(phoneNumber).username(username)
            .memberRole(MemberRole.CHILD).memberStatus(MemberStatus.INACTIVE).build();
    }

    public void updateChildMember(String password) {
        this.password = password;
        this.memberStatus = MemberStatus.ACTIVE;
    }


}
