package com.gdsc_solutionchallenge.backend.domain.route.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/route")
@Tag(name = "이동경로 API", description = "이동경로 API 모음")
public class RouteController {
}
