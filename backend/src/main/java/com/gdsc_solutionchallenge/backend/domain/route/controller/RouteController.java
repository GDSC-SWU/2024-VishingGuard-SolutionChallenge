package com.gdsc_solutionchallenge.backend.domain.route.controller;

import com.gdsc_solutionchallenge.backend.domain.route.domain.Route;
import com.gdsc_solutionchallenge.backend.domain.route.service.RouteService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/route")
@Tag(name = "Location API", description = "Collection of APIs for retrieving location information")
public class RouteController {
    private final RouteService routeService;

    /**
     * Retrieve list of routes (locations).
     *
     * @return ResponseEntity containing the list of routes on success, or an error response on failure.
     */
    @PostMapping("/")
    @Operation(summary = "Load Routes", description = "API to load available routes (locations)")
    public ResponseEntity<Object> loadRoutes() {
        try {
            List<Route> routes = routeService.loadRoute();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Routes loaded successfully", routes));
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
