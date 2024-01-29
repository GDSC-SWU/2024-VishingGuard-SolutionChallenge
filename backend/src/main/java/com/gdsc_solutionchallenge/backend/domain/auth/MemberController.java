package com.gdsc_solutionchallenge.backend.domain.auth;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.UserResponseDto;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignInRequestDto;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignUpRequestDto;
import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtToken;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "로그인 API", description = "로그인 API 모음")
public class MemberController {
    private final MemberService memberService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "로그인")
    public JwtToken signIn(@RequestBody SignInRequestDto signInDto) {
        String email = signInDto.getEmail(); // 이메일
        String password = signInDto.getPassword(); // 비번
        // 저장된 회원의 비밀번호를 가져옴
        Optional<User> memberOptional = userRepository.findByEmail(email);
        String storedPassword = memberOptional.map(User::getPassword)
                .orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED.value(), "User not found"));
//        System.out.println(storedPassword);
//        System.out.println(password);
        // 저장된 비밀번호와 클라이언트에서 전송된 비밀번호를 비교
        if (passwordEncoder.matches(password, storedPassword)) {
            // 비밀번호가 일치하면 로그인 성공 처리
            JwtToken jwtToken = memberService.signIn(email, storedPassword);
            log.info("request username = {}, password = {}", email, password);
            log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
            return jwtToken;
        } else {
            // 비밀번호가 일치하지 않으면 로그인 실패 처리
            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "Invalid password");
        }
    }

    @PostMapping("/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponseDto savedUserResponseDto = memberService.signUp(signUpRequestDto);
        return ResponseEntity.ok(savedUserResponseDto);
    }

}
