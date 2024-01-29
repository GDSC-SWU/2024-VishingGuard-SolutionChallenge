//package com.gdsc_solutionchallenge.backend.domain.board.post.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
//import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
//import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
//import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
//import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
//import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc(addFilters = false)
//class PostControllerTest {
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private PostRepository postRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void saveBoardTest() throws Exception {
//        List<User> users=userRepository.getAllUsers();
//
//        User user= userRepository.findByNickname("kim");
//
//        // Arrange
//        PostReqDto postReqDto = PostReqDto.builder()
//                .title("Valid Title")
//                .content("Valid Content")
////                .user(user)
//                .build();
//
//        String url="http://localhost:" + port + "/api/v1/posts";
//
//        // when
//        mockMvc.perform(post(url)
//                        .contentType(APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(postReqDto)))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void updateBoardTest() throws Exception {
//        User user= userRepository.findByNickname("kim");
//        Post post= postRepository.findById("CT9uwP2PmV93h2BnneIV");
//
//        String updateId = post.getId();
//        String expectedTitle = "title2";
//        String expectedContent = "content2";
//
////        PostUpdateReqDto postUpdateReqDto= PostUpdateReqDto.builder()
////                .title(expectedTitle)
////                .content(expectedContent)
////                .build();
//        PostUpdateReqDto postUpdateReqDto = new PostUpdateReqDto(expectedTitle,expectedContent);
//
//        String url="http://localhost:" + port + "/api/v1/posts/"+updateId;
//
//        // when
//        mockMvc.perform(put(url)
//                        .contentType(APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(postUpdateReqDto)))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//}