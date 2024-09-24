package com.firffin.payment.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.firffin.payment.global.error.exception.CustomException;
import com.firffin.payment.global.error.exception.ErrorCode;
import com.firffin.payment.global.security.PasswordEncoder;
import com.firffin.payment.member.domain.Member;
import com.firffin.payment.member.domain.MemberRole;
import com.firffin.payment.member.domain.MemberStatus;
import com.firffin.payment.member.dto.SignUpRequestDto;
import com.firffin.payment.member.dto.SignUpResponseDto;
import com.firffin.payment.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;
    private SignUpRequestDto parentSignUpRequest;
    private SignUpRequestDto childSignUpRequest;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

        // 부모 회원 가입 요청
        parentSignUpRequest = new SignUpRequestDto();
        parentSignUpRequest.setRole(MemberRole.PARENT.toString());
        parentSignUpRequest.setPhoneNumber("010-1234-5678");
        parentSignUpRequest.setUsername("parent");
        parentSignUpRequest.setPassword("parentPassword");
        
        // 자녀 회원 가입 요청
        childSignUpRequest = new SignUpRequestDto();
        childSignUpRequest.setRole(MemberRole.CHILD.toString());
        childSignUpRequest.setPhoneNumber("010-9876-5432");
        childSignUpRequest.setUsername("child");
        childSignUpRequest.setPassword("childPassword");

        when(passwordEncoder.hashPassword(anyString())).thenReturn("hashedPassword");

    }

    @Test
    void trySignUpParent_정상() {
        // given
        when(memberRepository.existsByPhoneNumber(parentSignUpRequest.getPhoneNumber())).thenReturn(false);

        // when
        SignUpResponseDto signUpdResponseDto = memberService.trySignUpMember(parentSignUpRequest);

        // then
        assertEquals(signUpdResponseDto.getUsername(), parentSignUpRequest.getUsername());

    }
    
    @Test
    void trySignUpParent_비정상_중복_전화번호() {
        // given
        when(memberRepository.existsByPhoneNumber(parentSignUpRequest.getPhoneNumber())).thenReturn(true);

        // when
        CustomException exception = assertThrows(CustomException.class,
                () -> memberService.trySignUpMember(parentSignUpRequest));

        // then
        assertEquals("이미 존재하는 핸드폰번호입니다.", exception.getMessage());
    }

    @Test
    void trySignUpChild_정상() {
        // given
        Member child = Member.createEmptyChild(childSignUpRequest.getPhoneNumber(), childSignUpRequest.getUsername());
        when(memberRepository.findByPhoneNumberAndUsernameAndMemberRoleAndMemberStatus(
                childSignUpRequest.getPhoneNumber(), childSignUpRequest.getUsername(),
                MemberRole.CHILD, MemberStatus.INACTIVE)).thenReturn(Optional.of(child));

        // when
        SignUpResponseDto signUpdResponseDto = memberService.trySignUpMember(childSignUpRequest);

        // then
        assertEquals(signUpdResponseDto.getUsername(), childSignUpRequest.getUsername());
    }

    @Test
    void trySignUpChild_비정상_존재하지_않는_자녀() {
        // given
        when(memberRepository.findByPhoneNumberAndUsernameAndMemberRoleAndMemberStatus(
                childSignUpRequest.getPhoneNumber(), childSignUpRequest.getUsername(),
                MemberRole.CHILD, MemberStatus.INACTIVE)).thenReturn(Optional.empty());
                
        // when
        CustomException exception = assertThrows(CustomException.class,
                () -> memberService.trySignUpMember(childSignUpRequest));

        // then
        assertEquals(ErrorCode.UNREGISTERED_CHILD, exception.getErrorCode());
        assertEquals("자녀 유저는 부모 유저가 먼저 등록해야합니다.", exception.getMessage());
    }
    
    @Test
    void trySignUpChild_비정상_틀린_권한() {
        // given
        SignUpRequestDto wrongRoleSignUpRequest = new SignUpRequestDto();
        wrongRoleSignUpRequest.setRole("random_role");
        wrongRoleSignUpRequest.setPhoneNumber("010-1234-5678");
        wrongRoleSignUpRequest.setUsername("random_username");
        wrongRoleSignUpRequest.setPassword("random_password");   

        // when
        CustomException exception = assertThrows(CustomException.class,
                () -> memberService.trySignUpMember(wrongRoleSignUpRequest));

        // then
        assertEquals(ErrorCode.INVALID_ROLE, exception.getErrorCode());
        assertEquals("유효한 권한이 아닙니다.", exception.getMessage());
    }
}

