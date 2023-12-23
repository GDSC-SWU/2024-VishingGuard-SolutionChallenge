package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.VishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class VishingControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private VishingRepository vishingRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("보이스 피싱 검사 테스트")
    public void vishingTest() throws Exception {
        // Given
        Vishing vishing1 = vishingRepository.saveVishing(Vishing.builder()
                .vishingKeyword("도용")
                .build());

        Vishing vishing2 = vishingRepository.saveVishing(Vishing.builder()
                .vishingKeyword("개인정보유출")
                .build());

        VishingRequestDto vishingRequestDto = VishingRequestDto.builder()
                .vishingScript("고객님의 명의가 도용되었습니다")
                .build();

        // When
        String url = "http://localhost:" + port + "/api/v1/result/vishing";

        // Then
        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vishingRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isBoolean())
                .andDo(print());

    }
}