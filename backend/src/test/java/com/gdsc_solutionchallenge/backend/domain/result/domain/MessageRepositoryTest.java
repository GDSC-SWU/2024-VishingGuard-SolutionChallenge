package com.gdsc_solutionchallenge.backend.domain.result.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    @DisplayName("피싱 문구 저장 & 불러오기")
    public void saveText_Load() throws Exception{
        final String text="HI";
        //Given
        Message savedMessage = messageRepository.saveMessage(Message.builder()
                .messageKeyword(text)
                .build());
        // When
        Message message=messageRepository.findById(savedMessage.getId());

        // Then
        assertThat(message.getMessageKeyword()).isEqualTo(savedMessage.getMessageKeyword());
    }

}