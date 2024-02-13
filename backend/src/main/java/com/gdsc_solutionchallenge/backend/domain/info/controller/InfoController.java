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
@Tag(name = "Prevention & Report Procedure & Report Place API", description = "API collection for Prevention, Report Procedure, and Report Place")
public class InfoController {
    private final InfoService infoService;

    @GetMapping("/reportProcedure")
    @Operation(summary = "Report Procedure", description = "Report Procedure API")
    public ResponseEntity<Object> loadReportProcedure(){
        try {
            // Load information about the report procedure
            List<ReportProcedure> reportProcedures = infoService.loadReportProcedure();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Report Procedure loaded successfully", reportProcedures));
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

    @GetMapping("/prevention")
    @Operation(summary = "Prevention", description = "Prevention API")
    public ResponseEntity<Object> loadPrevention(){
        try {
            // Load information about prevention measures
            List<Prevention> preventions = infoService.loadPrevention();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Prevention loaded successfully", preventions));
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

    @GetMapping("/reportPlace")
    @Operation(summary = "Report Place", description = "Report Place API")
    public ResponseEntity<Object> loadReportPlace(){
        try {
            // Load information about places to report
            List<ReportPlace> reportPlaces = infoService.loadReportPlace();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Report Place loaded successfully", reportPlaces));
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
