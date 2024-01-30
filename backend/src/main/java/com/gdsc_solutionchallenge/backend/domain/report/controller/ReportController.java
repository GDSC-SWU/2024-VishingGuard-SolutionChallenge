package com.gdsc_solutionchallenge.backend.domain.report.controller;

import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportSmsResDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportVisResDto;
import com.gdsc_solutionchallenge.backend.domain.report.service.ReportService;
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

    @PostMapping("/vishing/{userId}")
    @Operation(summary = "피싱 레포트 전화 결과", description = "피싱 레포트 전화 결과 API")
    public ResponseEntity<Object> identifyVishing(@PathVariable("userId") Long userId) {
        try {
            List<ReportVisResDto> reportVisResDtos = reportService.vishingReport(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 레포트 - 전화", reportVisResDtos));
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

    @GetMapping("/state/{userId}")
    @Operation(summary = "피싱 레포트 상태", description = "피싱 레포트 상태 API")
    public ResponseEntity<Object> getPhishingReportRiskStatus(@PathVariable("userId") Long userId) {
        try {
            String reportState = reportService.reportState(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 레포트 상태", reportState));
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
