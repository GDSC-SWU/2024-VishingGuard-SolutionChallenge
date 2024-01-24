package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingResDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.SmishingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
@Tag(name = "피싱 API", description = "피싱 API 모음")
public class SmishingController {
    private final SmishingService smishingService;

    @PostMapping("/smishing")
    @Operation(summary = "메시지 피싱", description = "파라미터로 받은 메시지 피싱 여부를 반환")
    public ResponseEntity<Object> postSmishing(@RequestBody SmishingReqDto smishingReqDto) {
        try {
            SmishingResDto smishingResDto = smishingService.isSmishing(smishingReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다", smishingResDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}