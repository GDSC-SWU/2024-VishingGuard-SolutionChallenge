package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.service.FSSOpenApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;

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
            String parsedFile = fssOpenApiService.fileParsing(responseData);
            String parsedFileName = fssOpenApiService.fileNameParsing(responseData);
            // | 기준으로 분리
            String[] fileUrls=parsedFile.split("\\|");
            String[] fileUrlsName=parsedFileName.split("\\|");

            // 파일 다운로드
            if (fileUrls != null && number >= 0 && number < fileUrls.length) {
                String fileUrl = fileUrls[number];
                String fileUrlName = fileUrlsName[number];

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileUrlName);

                byte[] fileContent = new RestTemplate().getForObject(fileUrl, byte[].class);

                return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ByteArrayResource("File not found".getBytes()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
