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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myPage")
@Tag(name = "마이페이지 API", description = "마이페이지 API 모음")
public class MyPageController {
    private final MyPageService myPageService;
    @PostMapping("/{userId}/modify")
    @Operation(summary = "정보 수정", description = "정보 수정 API")
    public ResponseEntity<Object> updateInfo(@PathVariable("userId") Long userId,
                                             @RequestBody UserUpdateReqDto userUpdateReqDto){
        try {
            String updatedUserName = myPageService.update(userId, userUpdateReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "정보 수정 완료", updatedUserName));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
        }
    }

    @PostMapping("/{userId}/withdraw")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
    public ResponseEntity<Object> withdraw(@PathVariable("userId") Long userId,
                                           @RequestBody UserWithdrawReqDto userWithdrawReqDto){
        try {
            String withdrawUserEmail = myPageService.withdraw(userId, userWithdrawReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "회원 탈퇴 완료", withdrawUserEmail));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
        }
    }
//
//    @PostMapping("/{userId}/logout")
//    @Operation(summary = "로그아웃", description = "로그아웃 API")
//    public ResponseEntity<Object> logout(){
//        try {
//            List<ReportProcedure> reportProcedures = myPageService.loadReportProcedure();
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new BaseResponse<>(HttpStatus.OK.value(), "로그아웃 완료", reportProcedures));
//        } catch (BaseException e) {
//            return ResponseEntity
//                    .status(e.getCode())
//                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
//        }
//    }
}
