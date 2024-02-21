package com.gdsc_solutionchallenge.backend.domain.auth.controller;

import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignInResDto;
import com.gdsc_solutionchallenge.backend.domain.auth.service.UserService;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.UserResponseDto;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignInRequestDto;
import com.gdsc_solutionchallenge.backend.domain.auth.dto.SignUpRequestDto;
import com.gdsc_solutionchallenge.backend.domain.auth.security.SecurityUtil;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Login API", description = "Collection of APIs for user authentication")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Endpoint for user sign-in.
     *
     * @param signInDto The request body containing email and password for authentication.
     * @return ResponseEntity with a BaseResponse containing the JWT token on successful login.
     */
    @PostMapping("/sign-in")
    @Operation(summary = "Login", description = "Authenticate user and generate JWT token")
    public ResponseEntity<Object> signIn(@RequestBody SignInRequestDto signInDto) {
        try {
            String email = signInDto.getEmail(); // Email
            String password = signInDto.getPassword(); // Password
            SignInResDto signInResDto = userService.getJwtToken(email, password);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Login successful", signInResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error" + e.getCause()));
        }
    }

    /**
     * Test endpoint to retrieve the current username using SecurityUtil.
     *
     * @return The current username.
     */
    @PostMapping("/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }

    /**
     * Endpoint for user sign-up.
     *
     * @param signUpRequestDto The request body containing user details for registration.
     * @return ResponseEntity with a BaseResponse containing user details on successful registration.
     */
    @PostMapping("/sign-up")
    @Operation(summary = "Sign Up", description = "Register a new user")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        try {
            UserResponseDto savedUserResponseDto = userService.signUp(signUpRequestDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Registration successful", savedUserResponseDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error" + e.getCause()));
        }
    }
}
