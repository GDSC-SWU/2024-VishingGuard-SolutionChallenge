package com.gdsc_solutionchallenge.backend.domain.board.heart.controller;

import com.gdsc_solutionchallenge.backend.domain.board.heart.dto.HeartReqDto;
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
@Tag(name = "좋아요 API", description = "좋아요 API 모음")
public class HeartController {
    private final HeartService heartService;
    @Operation(summary = "좋아요 설정", description = "좋아요 설정 API")
    @PostMapping("/{id}")
    public ResponseEntity<Object> setHeart(@PathVariable("id") String userId,@PathVariable("id") String postId){
        try {
            Boolean isHeart = heartService.isHeart(userId,postId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "좋아요 설정 완료", isHeart ));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"));
        }
    }
}
