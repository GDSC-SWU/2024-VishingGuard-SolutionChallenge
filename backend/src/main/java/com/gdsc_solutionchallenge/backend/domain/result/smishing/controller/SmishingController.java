package com.gdsc_solutionchallenge.backend.domain.result.smishing.controller;

import com.gdsc_solutionchallenge.backend.domain.result.smishing.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.dto.SmishingResDto;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.service.SmishingService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
@Tag(name = "스미싱 API", description = "스미싱 API 모음")
public class SmishingController {
    private final SmishingService smishingService;

    @PostMapping("/smishing")
    @Operation(summary = "메시지 피싱", description = "메시지가 왔을때 피싱 메시지 스크립트 저장 및 피싱 여부 반환")
    public ResponseEntity<Object> saveAndCheckSmishing(@RequestBody SmishingReqDto smishingReqDto) {
        try {
            SmishingResDto smishingResDto = smishingService.whySmishing(smishingReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다", smishingResDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}