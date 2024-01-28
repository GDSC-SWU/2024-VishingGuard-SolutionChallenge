package com.gdsc_solutionchallenge.backend.domain.report.controller;

import com.gdsc_solutionchallenge.backend.domain.report.service.ReportService;
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
@RequestMapping("/api/v1/report")
@Tag(name = "피싱 위험 레포트 API", description = "피싱 위험 레포트 API 모음")
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/smishing")
    @Operation(summary = "메시지 피싱", description = "피싱 레포트에서 파라미터로 받은 메시지 피싱 여부와 이유를 반환")
    public ResponseEntity<Object> identifySmishing(@RequestBody SmishingReqDto smishingReqDto) {
        try {
            SmishingResDto smishingResDto = reportService.whySmishing(smishingReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다", smishingResDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
