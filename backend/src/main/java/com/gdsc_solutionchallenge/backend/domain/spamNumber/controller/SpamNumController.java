package com.gdsc_solutionchallenge.backend.domain.spamNumber.controller;

import com.gdsc_solutionchallenge.backend.domain.spamNumber.dto.SpamNumReqDto;
import com.gdsc_solutionchallenge.backend.domain.spamNumber.dto.SpamNumResDto;
import com.gdsc_solutionchallenge.backend.domain.spamNumber.service.SpamNumService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
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
@Tag(name = "SpamNumber API", description = "Collection of SpamNumber APIs")
public class SpamNumController {
    private final SpamNumService spamNumService;

    /**
     * Determine if a phone number is spam.
     *
     * @param spamNumReqDto DTO containing the phone number to be checked for spam.
     * @return ResponseEntity containing the result of the spam check.
     */
    @PostMapping("/spamNumber")
    @Operation(summary = "Detect Spam Phone Number", description = "Determine whether the phone number is spam")
    public ResponseEntity<Object> postSpam(@RequestBody SpamNumReqDto spamNumReqDto) {
        try {
            SpamNumResDto responseDto = spamNumService.isSpamNum(spamNumReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Spam detection result", responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
