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

    // Sign in
    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. Create an Authentication object based on username + password
        // The authentication has an initial authenticated value of false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. Perform actual verification. Call authenticate() to validate the requested Member
        // The loadUserByUsername method from CustomUserDetailsService is executed during authenticate
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. Generate JWT token based on authentication information
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    // Sign up
    @Transactional
    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new BaseException(HttpStatus.CONFLICT.value(), "Username already exists.");
        }
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new BaseException(HttpStatus.CONFLICT.value(), "Email is already registered.");
        }
        // Encrypt Password
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");  // Assign USER role
        return UserResponseDto.toDto(userRepository.save(signUpRequestDto.toEntity(encodedPassword, roles)));
    }

    @Transactional
    public SignInResDto getJwtToken(String email, String password) {
        // Retrieve information of the stored user
        Optional<User> memberOptional = userRepository.findByEmail(email);
        User user = memberOptional.orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED.value(), "User not found"));

        // Compare stored password with the password sent from the client
        if (passwordEncoder.matches(password, user.getPassword())) {
            // If passwords match, proceed with successful login
            JwtToken jwtToken = signIn(email, user.getPassword());

            // Additional logic to get the user's ID here
            Long userId = user.getId();

            log.info("request username = {}, password = {}", email, password);
            log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

            // Return the user's ID and token
            return new SignInResDto(jwtToken, userId);
        } else {
            // If passwords do not match, handle login failure
            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "Invalid password");
        }
    }
}
