package com.gdsc_solutionchallenge.backend.domain.mypage.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.mypage.dto.UserUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.mypage.dto.UserWithdrawReqDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Update user information including username and password.
     *
     * @param userId            ID of the user to be updated.
     * @param userUpdateReqDto  DTO containing updated user information.
     * @return                  Updated username.
     * @throws Exception        If the user is not found or an error occurs during the update.
     */
    public String update(Long userId, UserUpdateReqDto userUpdateReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(userUpdateReqDto.getPassword());
        user.updatePassword(encodedPassword);
        System.out.println(userUpdateReqDto.getUsername());
        user.updateUsername(userUpdateReqDto.getUsername());

        userRepository.save(user);

        return user.getUsername();
    }

    /**
     * Withdraw the user by deleting the account.
     *
     * @param userId                ID of the user to be withdrawn.
     * @param userWithdrawReqDto    DTO containing user information for withdrawal.
     * @return                      Email of the withdrawn user.
     * @throws Exception            If the user is not found, password mismatch, or an error occurs during withdrawal.
     */
    public String withdraw(Long userId, UserWithdrawReqDto userWithdrawReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));
        String email = user.getEmail();

        // Encrypt the entered password for comparison
        System.out.println(userWithdrawReqDto.getPassword());
        System.out.println(user.getPassword());

        // Password mismatch
        if (!passwordEncoder.matches(userWithdrawReqDto.getPassword(), user.getPassword())) {
            throw new BaseException(HttpStatus.FORBIDDEN.value(), "Password mismatch");
        }

        userRepository.delete(user);

        return email;
    }

    /**
     * Logout the user by clearing the security context.
     *
     * @param userId        ID of the user to be logged out.
     * @return              Username of the logged-out user.
     * @throws Exception    If the user is not found during logout.
     */
    public String logout(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        SecurityContextHolder.clearContext();

        return user.getUsername();
    }
}
