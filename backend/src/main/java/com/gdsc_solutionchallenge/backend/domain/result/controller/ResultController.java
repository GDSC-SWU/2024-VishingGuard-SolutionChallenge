package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.ResultService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
@Tag(name = "Smishing API", description = "Smishing API Collection")
public class ResultController {
    private final ResultService resultService;

    /**
     * Save and check for Smishing (SMS phishing) messages.
     *
     * @param userId          User ID for saving and checking Smishing.
     * @param smishingReqDto  Smishing request data containing the message and timestamp.
     * @return ResponseEntity indicating the Smishing result.
     */
    @PostMapping("/smishing/{userId}")
    @Operation(summary = "SMS Phishing", description = "Save and check for Smishing messages")
    public ResponseEntity<Object> saveAndCheckSmishing(@PathVariable("userId") Long userId,
                                                       @RequestBody SmishingReqDto smishingReqDto) {
        try {
            Boolean isSmishing = resultService.isSmishing(userId, smishingReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Phishing result", isSmishing));
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

    /**
     * Save Vishing (voice phishing) script when a Vishing call is received.
     *
     * @param userId          User ID for saving Vishing script.
     * @param vishingReqDto   Vishing request data containing the script and timestamp.
     * @return ResponseEntity indicating the successful saving of Vishing script.
     */
    @PostMapping("/vishing/{userId}")
    @Operation(summary = "Vishing (Voice Phishing)", description = "Save Vishing script when a Vishing call is received")
    public ResponseEntity<Object> saveAndCheckVishing(@PathVariable("userId") Long userId,
                                                       @RequestBody VishingReqDto vishingReqDto) {
        try {
            resultService.saveVishing(userId, vishingReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Vishing script saved successfully"));
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
