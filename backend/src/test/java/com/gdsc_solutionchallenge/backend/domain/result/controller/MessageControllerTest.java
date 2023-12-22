package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_solutionchallenge.backend.domain.result.domain.MessageRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
//@Transactional
class MessageControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("문자 피싱 검사 테스트")
    public void testMessagePhishing() throws Exception {
        // Given
        /*Message message1 = messageRepository.saveMessage(Message.builder()
                .messageKeyword("엄마")
                .build());

        Message message2 = messageRepository.saveMessage(Message.builder()
                .messageKeyword("기프트 카드")
                .build());*/

        SmishingRequestDto smishingRequestDto = SmishingRequestDto.builder()
                .message("대포통장에 기프트 카드")
                .build();

        // When
        String url = "http://localhost:" + port + "/api/v1/result/message";

        // Then
        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(smishingRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean())
                .andDo(print());

    }
}