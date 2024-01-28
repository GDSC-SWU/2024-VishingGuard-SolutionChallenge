//package com.gdsc_solutionchallenge.backend.domain.user.service;
//
//
//import com.gdsc_teamb.servertoyproject.domain.user.domain.UserEntity;
//import com.gdsc_teamb.servertoyproject.domain.user.domain.UserRepository;
//import com.gdsc_teamb.servertoyproject.exception.AppException;
//import com.gdsc_teamb.servertoyproject.exception.ErrorCode;
//import com.gdsc_teamb.servertoyproject.web.dto.UserResponseDto;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder encoder;
//
//    @Transactional
//    public UserResponseDto join(String email, String nickname, String password, String phone) {
//
//        // email 중복 체크
//        userRepository.findByEmail(email)
//                .ifPresent(userEntity -> {
//                    throw new AppException(ErrorCode.USER_EMAIL_DUPLICATION);
//                });
//        // nickname 중복 체크
//        userRepository.findByNickname(nickname)
//                .ifPresent(userEntity -> {
//                    throw new AppException(ErrorCode.USER_NICKNAME_DUPLICATION);
//                });
//
//
//        // 저장
//        UserEntity user = UserEntity.builder()
//                .email(email)
//                .password(encoder.encode(password))
//                .nickname(nickname)
//                .phone(phone)
//                .build();
//
//        userRepository.save(user);
//
//        return UserResponseDto.builder()
//                .email(email)
//                .nickname(nickname)
//                .phone(phone)
//                .build();
//    }
//
//
//    // email, nickname 변경
//    @Transactional
//    public UserResponseDto update(String email, Optional<String> nickname, Optional<String> phone) throws Exception {
//
//        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
//                new AppException(ErrorCode.NOT_FOUND_USER));
//
//        String updatedNickname = nickname.orElse(user.getNickname());
//        String updatedPhone = phone.orElse(user.getPhone());
//
//        if (nickname.isPresent()) {
//            user.updateNickname(updatedNickname);
//        }
//        if (phone.isPresent()) {
//            user.updatePhone(updatedPhone);
//        }
//
//        return UserResponseDto.builder()
//                .email(email)
//                .nickname(updatedNickname)
//                .phone(updatedPhone)
//                .build();
//    }
//
//
//    // 비밀번호 변경
//    public void updatePassword(String email, String checkPassword, String toBePassword) throws Exception {
//        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
//                new AppException(ErrorCode.NOT_FOUND_USER)); // 존재하지 않는 유저
//
//        // 비밀번호 불일치
//        if (!user.matchPassword(encoder, checkPassword)) {
//            throw new AppException(ErrorCode.NOT_EQUAL_INPUT_PASSWORD);
//        }
//
//        user.updatePassword(encoder, toBePassword);
//
//    }
//
//    // 회원 탈퇴
//    public void withdraw(String email, String checkPassword) throws Exception {
//        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
//                new AppException(ErrorCode.NOT_FOUND_USER)); // 존재하지 않는 유저
//
//        // 비밀번호 불일치
//        if (!user.matchPassword(encoder, checkPassword)) {
//            throw new AppException(ErrorCode.NOT_EQUAL_INPUT_PASSWORD);
//        }
//
//        userRepository.delete(user);
//
//    }
//
//
//}