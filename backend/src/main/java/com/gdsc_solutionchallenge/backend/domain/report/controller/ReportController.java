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
@Tag(name = "Phishing Risk Report API", description = "Phishing Risk Report API Collection")
public class ReportController {
    private final ReportService reportService;

    /**
     * Endpoint for identifying phishing through SMS.
     *
     * @param userId User ID for phishing report.
     * @return ResponseEntity containing the phishing report for SMS.
     */
    @PostMapping("/smishing/{userId}")
    @Operation(summary = "Phishing Report - SMS Result", description = "Phishing Report - SMS Result API")
    public ResponseEntity<Object> identifySmishing(@PathVariable("userId") Long userId) {
        try {
            List<ReportSmsResDto> reportSmsReqDto = reportService.smishingReport(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Phishing Report - SMS", reportSmsReqDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error: " + e.getMessage()));
        }
    }

    /**
     * Endpoint for identifying phishing through phone calls (vishing).
     *
     * @param userId User ID for phishing report.
     * @return ResponseEntity containing the phishing report for phone calls (vishing).
     */
    @PostMapping("/vishing/{userId}")
    @Operation(summary = "Phishing Report - Vishing Result", description = "Phishing Report - Vishing Result API")
    public ResponseEntity<Object> identifyVishing(@PathVariable("userId") Long userId) {
        try {
            List<ReportVisResDto> reportVisResDtos = reportService.vishingReport(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Phishing Report - Vishing", reportVisResDtos));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error: " + e.getMessage()));
        }
    }

    /**
     * Endpoint for retrieving the phishing report status.
     *
     * @param userId User ID for phishing report.
     * @return ResponseEntity containing the phishing report status.
     */
    @GetMapping("/state/{userId}")
    @Operation(summary = "Phishing Report Status", description = "Phishing Report Status API")
    public ResponseEntity<Object> getPhishingReportRiskStatus(@PathVariable("userId") Long userId) {
        try {
            String reportState = reportService.reportState(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Phishing Report Status", reportState));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error: " + e.getMessage()));
        }
    }
}
