package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.gdsc_solutionchallenge.backend.domain.result.domain.MessageRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MessageService {
    public final MessageRepository messageRepository;

    public ResponseEntity<Object> isPhishingMessage(SmishingRequestDto smishingRequestDto){
        try{
            List<Message> messageList=messageRepository.getAllMessages();

            for (Message message: messageList){
                if (message != null && removeSpacesAndLowercase(smishingRequestDto.getSmishingScript())
                        .contains(removeSpacesAndLowercase(message.getMessageKeyword())))
                {
                    return ResponseEntity.ok(true);
                }

            }
            return ResponseEntity.ok(false);
        }catch (Exception e){
            // 예외 발생 시, 클라이언트에게 400 Bad Request 와 함께 예외 메시지를 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String removeSpacesAndLowercase(String input) {
        return input.replaceAll("\\s", "").toLowerCase();
    }
}
