package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.MessageService;
import com.gdsc_solutionchallenge.backend.domain.result.service.VishingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
@Tag(name = "Vishing API", description = "Vishing API")
public class VishingController {
    private final VishingService vishingService;

    @PostMapping("/vishing")
    @Operation(summary = "전화 피싱", description = "파라미터로 받은 전화 대본 피싱 여부를 반환")
    @Parameter(name = "전화", description = "피싱 검사할 전화 내용 DTO")
    public ResponseEntity<Object> vishing(@RequestBody VishingRequestDto vishingRequestDto){
        return vishingService.isVishing(vishingRequestDto);
    }
}