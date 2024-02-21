package com.gdsc_solutionchallenge.backend.domain.home.banner.controller;

import com.gdsc_solutionchallenge.backend.domain.home.banner.service.FSSOpenApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "FSS API", description = "FSS Open API Invocation API")
public class FSSOpenApiController {
    private final FSSOpenApiService fssOpenApiService;
    @Value("${fss.api.url}")
    private String apiUrl;

    @GetMapping("/financial/download/{number}")
    @Operation(summary = "FSS API", description = "Communicate with FSS API and Download Files")
    public ResponseEntity<Object> downloadFile(@PathVariable("number") int number){
        try {
            // Use RestTemplate to send a GET request
            RestTemplate restTemplate = new RestTemplate();
            String responseData = restTemplate.getForObject(apiUrl, String.class);

            // Parse the received data
            String parsedFile = fssOpenApiService.fileParsing(responseData);
            String parsedFileName = fssOpenApiService.fileNameParsing(responseData);

            // Split using the "|" delimiter
            String[] fileUrls = parsedFile.split("\\|");
            String[] fileUrlsName = parsedFileName.split("\\|");

            // File download
            if (fileUrls != null && number >= 0 && number < fileUrls.length) {
                String fileUrl = fileUrls[number];
                String fileUrlName = fileUrlsName[number];

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileUrlName, StandardCharsets.UTF_8.toString()));
                System.out.println("Content-Disposition: " + headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));

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
