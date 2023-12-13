package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.gdsc_solutionchallenge.backend.domain.result.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
public class MessageController {
    private final MessageService messageService;

    // Json객체 -> Dto로 전환 후, service 단으로 이동
    @PostMapping("/message")
    public ResponseEntity<Object> phishingMessage(@RequestBody MessageRequestDto messageRequestDto)throws ExecutionException, InterruptedException {
        return messageService.isPhishingMessage(messageRequestDto);
    }
}
