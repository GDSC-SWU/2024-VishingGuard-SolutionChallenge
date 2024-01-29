package com.gdsc_solutionchallenge.backend.domain.report.controller;

import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportSmsResDto;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportVisReqDto;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportVisResDto;
import com.gdsc_solutionchallenge.backend.domain.report.service.ReportService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Tag(name = "피싱 위험 레포트 API", description = "피싱 위험 레포트 API 모음")
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/smishing/{userId}")
    @Operation(summary = "피싱 레포트 문자 결과", description = "피싱 레포트 문자 결과 API")
    public ResponseEntity<Object> identifySmishing(@PathVariable("userId") Long userId) {
        try {
            List<ReportSmsResDto> reportSmsReqDto = reportService.smishigReport(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 레포트 - 문자", reportSmsReqDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/vishing/{userId}")
    @Operation(summary = "피싱 레포트 전화 결과", description = "피싱 레포트 전화 결과 API")
    public ResponseEntity<Object> identifySmishing(@PathVariable("userId") Long userId,
                                                   @RequestBody ReportVisReqDto reportVisReqDto) {
        try {
            List<ReportVisResDto> reportVisResDtos = reportService.vishingReport(userId, reportVisReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다", reportVisResDtos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
