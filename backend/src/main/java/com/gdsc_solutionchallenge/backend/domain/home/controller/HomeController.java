package com.gdsc_solutionchallenge.backend.domain.home.controller;

import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.service.PostService;
import com.gdsc_solutionchallenge.backend.domain.home.dto.HomeResDto;
import com.gdsc_solutionchallenge.backend.domain.home.service.HomeService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
@Tag(name = "Home API", description = "Collection of APIs for the home screen")
public class HomeController {
    private final HomeService homeService;

    /**
     * API endpoint to load the home screen.
     *
     * @return ResponseEntity containing the result of the home screen loading.
     */
    @PostMapping("/")
    @Operation(summary = "Load Home", description = "API to load the home screen")
    public ResponseEntity<Object> loadHome() {
        try {
            List<HomeResDto> homeResDto = homeService.loadHome();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Home screen loaded successfully", homeResDto));
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
