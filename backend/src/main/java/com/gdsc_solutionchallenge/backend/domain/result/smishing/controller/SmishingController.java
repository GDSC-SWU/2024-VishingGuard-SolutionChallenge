package com.gdsc_solutionchallenge.backend.domain.result.smishing.controller;

import com.gdsc_solutionchallenge.backend.domain.result.smishing.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.service.SmishingService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/smishing")
@Tag(name = "스미싱 API", description = "스미싱 API 모음")
public class SmishingController {
    private final SmishingService smishingService;

    @PostMapping("/{userId}")
    @Operation(summary = "메시지 피싱", description = "메시지가 왔을때 피싱 메시지 스크립트 저장 및 피싱 여부 반환")
    public ResponseEntity<Object> saveAndCheckSmishing(@PathVariable("userId") Long userId,
                                                       @RequestBody SmishingReqDto smishingReqDto) {
        try {
            Boolean isSmishing = smishingService.isSmishing(userId, smishingReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다", isSmishing));
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
}