package com.gdsc_solutionchallenge.backend.domain.post.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_solutionchallenge.backend.domain.post.board.domain.BoardRepository;
import com.gdsc_solutionchallenge.backend.domain.post.board.dto.BoardReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class BoardControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveBoardTest() throws Exception {
        List<User> users=userRepository.getAllUsers();

        User user= userRepository.findByNickname("kim");

        // Arrange
        BoardReqDto boardReqDto = BoardReqDto.builder()
                .title("Valid Title")
                .content("Valid Content")
                .user(user)
                .build();

        String url="http://localhost:" + port + "/api/boards";

        // when
        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(boardReqDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}