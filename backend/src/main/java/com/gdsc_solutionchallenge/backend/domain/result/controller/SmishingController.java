package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.SmishingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
@Tag(name = "Message API", description = "Message API")
public class SmishingController {
    private final SmishingService smishingService;

    @PostMapping("/smishing")
    @Operation(summary = "메시지 피싱", description = "파라미터로 받은 메시지 피싱 여부를 반환")
    @Parameter(name = "메시지", description = "피싱 검사할 메시지 DTO")
    public ResponseEntity<Object> postSmishing(@RequestBody SmishingRequestDto smishingRequestDto) {
        try {
            boolean result = smishingService.isSmishing(smishingRequestDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}