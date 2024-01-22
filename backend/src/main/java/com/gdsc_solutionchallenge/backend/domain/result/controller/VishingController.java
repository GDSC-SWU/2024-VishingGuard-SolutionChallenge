package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.VishingService;
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
@Tag(name = "피싱 API", description = "피싱 API 모음")
public class VishingController {
    private final VishingService vishingService;

    @PostMapping("/vishing")
    @Operation(summary = "전화 피싱", description = "파라미터로 받은 전화 대본 피싱 여부를 반환")
    public ResponseEntity<Object> postVishing(@RequestBody VishingRequestDto vishingRequestDto) throws Exception {
        try{
            boolean result=vishingService.isVishing(vishingRequestDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다",result));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}