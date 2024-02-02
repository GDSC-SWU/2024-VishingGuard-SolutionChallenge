package com.gdsc_solutionchallenge.backend.domain.info.controller;

import com.gdsc_solutionchallenge.backend.domain.info.domain.Prevention;
import com.gdsc_solutionchallenge.backend.domain.info.domain.ReportPlace;
import com.gdsc_solutionchallenge.backend.domain.info.domain.ReportProcedure;
import com.gdsc_solutionchallenge.backend.domain.info.service.InfoService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/info")
@Tag(name = "사전 예방 & 신고 절차 & 신고처 API", description = "사전 예방 & 신고 절차 & 신고처 API 모음")
public class InfoController {
    private final InfoService infoService;

    @GetMapping("/report_procedure")
    @Operation(summary = "신고 절차", description = "신고 절차 API")
    public ResponseEntity<Object> loadReportProcedure(){
        try {
            List<ReportProcedure> reportProcedures = infoService.loadReportProcedure();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "신고 절차 로딩 완료", reportProcedures));
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

    @GetMapping("/prevention")
    @Operation(summary = "사전 예방", description = "사전 예방 API")
    public ResponseEntity<Object> loadPrevention(){
        try {
            List<Prevention> preventions = infoService.loadPrevention();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "사전 예방 로딩 완료", preventions));
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

    @GetMapping("/report_place")
    @Operation(summary = "신고처", description = "신고처 API")
    public ResponseEntity<Object> loadReportPlace(){
        try {
            List<ReportPlace> reportPlaces = infoService.loadReportPlace();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "신고처 로딩 완료", reportPlaces));
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
