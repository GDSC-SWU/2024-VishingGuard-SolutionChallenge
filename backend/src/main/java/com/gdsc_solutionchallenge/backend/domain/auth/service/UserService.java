package com.gdsc_solutionchallenge.backend.domain.auth.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignInResDto;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.UserResponseDto;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignUpRequestDto;
import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtToken;
import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtTokenProvider;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new BaseException(HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임입니다.");
        }
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new BaseException(HttpStatus.CONFLICT.value(), "이미 가입된 이메일입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");  // USER 권한 부여
        return UserResponseDto.toDto(userRepository.save(signUpRequestDto.toEntity(encodedPassword, roles)));
    }

    @Transactional
    public SignInResDto getJwtToken(String email, String password) {
        // 저장된 회원의 정보를 가져옴
        Optional<User> memberOptional = userRepository.findByEmail(email);
        User user = memberOptional.orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED.value(), "User not found"));

        // 저장된 비밀번호와 클라이언트에서 전송된 비밀번호를 비교
        if (passwordEncoder.matches(password, user.getPassword())) {
            // 비밀번호가 일치하면 로그인 성공 처리
            JwtToken jwtToken = signIn(email, user.getPassword());

            // 여기에 회원의 ID를 가져오는 로직 추가
            Long userId = user.getId();

            log.info("request username = {}, password = {}", email, password);
            log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

            // 회원의 ID와 토큰을 반환
            return new SignInResDto(jwtToken, userId);
        } else {
            // 비밀번호가 일치하지 않으면 로그인 실패 처리
            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "Invalid password");
        }
    }


}