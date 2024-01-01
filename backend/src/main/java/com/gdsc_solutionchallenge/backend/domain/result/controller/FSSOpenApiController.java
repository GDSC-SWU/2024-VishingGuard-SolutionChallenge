package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.domain.result.dto.OpenApiDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.FSSOpenApiService;
import com.gdsc_solutionchallenge.backend.domain.result.service.SmishingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "금감원 API", description = "금감원 open api 호출 API")
public class FSSOpenApiController {
    private final FSSOpenApiService fssOpenApiService;
    @Value("${fss.api.url}")
    private String apiUrl;
    @GetMapping("/financial/download/{number}")
    @Operation(summary = "금감원 API", description = "금감원 API 통신 및 파일 다운로드")
    public ResponseEntity<Object> downloadFile(@PathVariable("number") int number){
        try {
            // RestTemplate을 사용하여 GET 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            String responseData = restTemplate.getForObject(apiUrl, String.class);
            // 응답받은 data parsing 진행
            String parsedData = fssOpenApiService.dataParsing(responseData);
            // | 기준으로 분리
            String[] urls=parsedData.split("\\|");

            // 파일 다운로드
            if (urls != null && number >= 0 && number < urls.length) {
                String fileUrl = urls[number];

                // 파일의 URL을 반환
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new BaseResponse<>(HttpStatus.OK.value(), "다운로드 URL입니다", fileUrl));

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(urls[0]);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /*@GetMapping("/financial/download/{number}")
    @Operation(summary = "금감원 파일 다운로드", description = "금감원 파일 다운로드")
    @Parameter(name = "파일 번호", description = "다운로드 할 파일 번호")
    public ResponseEntity<Object> downloadFile(@PathVariable("number") int number){
        try {
            Object result = fssOpenApiService.downloadFile(urls[number]);
            if (urls != null && number >= 0 && number < urls.length) {
                String fileUrl = urls[number];

                Path filePath = Paths.get(fileUrl);
                Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new BaseResponse<>(HttpStatus.OK.value(), "피싱 결과입니다", resource));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(urls[0]);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }*/
}
