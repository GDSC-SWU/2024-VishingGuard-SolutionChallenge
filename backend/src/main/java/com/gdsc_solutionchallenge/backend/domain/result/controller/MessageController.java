package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.gdsc_solutionchallenge.backend.domain.result.dto.MessageRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.service.MessageService;
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
@Tag(name = "Message API", description = "Message API")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/message")
    @Operation(summary = "메시지 피싱", description = "파라미터로 받은 메시지 피싱 여부를 반환")
    @Parameter(name = "메시지", description = "피싱 검사할 메시지 DTO")
    public ResponseEntity<Object> phishingMessage(@RequestBody MessageRequestDto messageRequestDto){
        return messageService.isPhishingMessage(messageRequestDto);
    }
}
