package com.gdsc_solutionchallenge.backend.domain.auth;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.Member;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.MemberRepository;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.MemberResponseDto;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignUpRequestDto;
import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtToken;
import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtTokenProvider;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    // 회원가입
    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new BaseException(HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임입니다.");
        }
        if (memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new BaseException(HttpStatus.CONFLICT.value(), "이미 가입된 이메일입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");  // USER 권한 부여
        return MemberResponseDto.toDto(memberRepository.save(signUpRequestDto.toEntity(encodedPassword, roles)));
    }


}