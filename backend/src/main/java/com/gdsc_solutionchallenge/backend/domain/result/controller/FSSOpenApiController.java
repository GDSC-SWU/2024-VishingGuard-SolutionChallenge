package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.dto.FSSOpenApiDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.FSSOpenApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

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
            List<FSSOpenApiDto> OpenApiDto = fssOpenApiService.dataParsing(responseData);
            return ResponseEntity.ok(OpenApiDto);
            // | 기준으로 분리
            //String[] urls=OpenApiDto.split("\\|");

            // 파일 다운로드
//            if (urls != null && number >= 0 && number < urls.length) {
//                String fileUrl = urls[number];
//
//                // 파일의 내용을 바이트 배열로 읽어옴
//                byte[] fileContent = restTemplate.getForObject(fileUrl, byte[].class);
//
//                // 바이트 배열을 이용하여 ByteArrayResource 생성
//                ByteArrayResource resource = new ByteArrayResource(fileContent);
//
//                // 파일의 Content-Type을 지정하여 응답
//                return ResponseEntity
//                        .ok()
//                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                        .body(resource);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(urls[0]);
//            }
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
