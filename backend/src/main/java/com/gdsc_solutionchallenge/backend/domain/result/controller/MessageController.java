package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.gdsc_solutionchallenge.backend.domain.result.dto.MessageRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
@Tag(name = "예제 API", description = "Swagger 테스트용 API")
public class MessageController {
    private final MessageService messageService;

    // Json객체 -> Dto로 전환 후, service 단으로 이동
    @PostMapping("/message")
    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "str", description = "2번 반복할 문자열")
    public ResponseEntity<Object> phishingMessage(@RequestBody MessageRequestDto messageRequestDto){
        return messageService.isPhishingMessage(messageRequestDto);
    }
}
