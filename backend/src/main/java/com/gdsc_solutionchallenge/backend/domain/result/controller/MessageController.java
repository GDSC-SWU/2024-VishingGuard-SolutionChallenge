package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.gdsc_solutionchallenge.backend.domain.result.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
public class MessageController {
    private final MessageService messageService;

    // Json객체 -> Dto로 전환 후, service 단으로 이동
    @PostMapping("/message")
    public List<Message> phishingMessage(/*@RequestBody MessageRequestDto messageRequestDto*/){
        //return messageService.isPhishingMessage(messageRequestDto);
        List<Message> message=messageService.getMessage();
        return message;
    }
}
