package com.gdsc_solutionchallenge.backend.domain.result.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "금감원 API", description = "금감원 open api 호출 API")
public class FSSOpenApiController {
    @Value("${fss.api.url}")
    private String apiUrl;
    @GetMapping("/financial")
    @Operation(summary = "금감원 API", description = "금감원 API")
    public ResponseEntity<Object> getDataFromExternalApi() {
        try {
            // RestTemplate을 사용하여 GET 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            String responseData = restTemplate.getForObject(apiUrl, String.class);

            // 성공적인 응답을 클라이언트에 반환
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            // 오류 발생 시, 클라이언트에게 500 Internal Server Error와 함께 예외 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
