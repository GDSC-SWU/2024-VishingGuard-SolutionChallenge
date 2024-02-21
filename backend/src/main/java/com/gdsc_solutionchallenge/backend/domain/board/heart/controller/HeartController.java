package com.gdsc_solutionchallenge.backend.domain.board.heart.controller;

import com.gdsc_solutionchallenge.backend.domain.board.heart.dto.HeartResDto;
import com.gdsc_solutionchallenge.backend.domain.board.heart.service.HeartService;
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
@RequestMapping("/api/v1/heart")
@Tag(name = "Like API", description = "Collection of APIs for managing likes")
public class HeartController {
    private final HeartService heartService;

    // API endpoint for setting a like on a post
    @Operation(summary = "Set Like", description = "API to set a like on a post")
    @PostMapping("/{userId}/{postId}/create")
    public ResponseEntity<Object> setHeart(@PathVariable("userId") Long userId,
                                           @PathVariable("postId") String postId){
        try {
            // Call the HeartService to check and set the like
            HeartResDto heart = heartService.isHeart(userId, postId);

            // Return a success response with the HeartResDto
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Like set successfully", heart));
        } catch (BaseException e) {
            // Return an error response for BaseException
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            // Return an internal server error response for other exceptions
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }
}
