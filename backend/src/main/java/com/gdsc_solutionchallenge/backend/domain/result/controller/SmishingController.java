package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.SmishingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Object> phishingMessage(@RequestBody SmishingRequestDto smishingRequestDto){
        return smishingService.isSmishing(smishingRequestDto);
    }
}
