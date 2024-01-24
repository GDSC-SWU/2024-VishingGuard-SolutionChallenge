package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SpamNumReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SpamNumResDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.SpamNumService;
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
public class SpamNumController {
    private final SpamNumService spamNumService;

    @PostMapping("/spamNumber")
    @Operation(summary = "스팸 전화번호 판단", description = "스팸 전화번호 여부를 판단")
    public ResponseEntity<Object> postSpam(@RequestBody SpamNumReqDto spamNumReqDto) {
        try {
            SpamNumResDto responseDto= spamNumService.isSpamNum(spamNumReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "스팸 판단 결과입니다", responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
