package com.gdsc_solutionchallenge.backend.domain.mypage.controller;

import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.info.domain.ReportProcedure;
import com.gdsc_solutionchallenge.backend.domain.mypage.dto.UserUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.mypage.dto.UserWithdrawReqDto;
import com.gdsc_solutionchallenge.backend.domain.mypage.service.MyPageService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myPage")
@Tag(name = "MyPage API", description = "MyPage API Collection")
public class MyPageController {
    private final MyPageService myPageService;

    @PostMapping("/{userId}/modify")
    @Operation(summary = "Update Information", description = "API to update user information")
    public ResponseEntity<Object> updateInfo(@PathVariable("userId") Long userId,
                                             @RequestBody UserUpdateReqDto userUpdateReqDto){
        try {
            String updatedUserName = myPageService.update(userId, userUpdateReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Information updated successfully", updatedUserName));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }

    @PostMapping("/{userId}/withdraw")
    @Operation(summary = "Withdraw Membership", description = "API to withdraw user membership")
    public ResponseEntity<Object> withdraw(@PathVariable("userId") Long userId,
                                           @RequestBody UserWithdrawReqDto userWithdrawReqDto){
        try {
            String withdrawUserEmail = myPageService.withdraw(userId, userWithdrawReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Membership withdrawal completed", withdrawUserEmail));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }

    @PostMapping("/{userId}/logout")
    @Operation(summary = "Logout", description = "API to logout user")
    public ResponseEntity<Object> logout(@PathVariable("userId") Long userId){
        try {
            String logoutedUserName = myPageService.logout(userId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                System.out.println("Logged out");
            } else {
                // The user is currently logged in.
                System.out.println("Logged in");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Logout completed", logoutedUserName));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }
}
