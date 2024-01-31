package com.gdsc_solutionchallenge.backend.domain.home.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
import com.gdsc_solutionchallenge.backend.domain.home.dto.HomeResDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final PostRepository postRepository;

    public List<HomeResDto> loadHome() throws Exception {
        // post 레포에서 게시글의 title 전부 빼와서  Dto List 생성
        List<String> allTitles = postRepository.getAllTitles();
        List<HomeResDto> homeResDtos = allTitles.stream()
                .map(title -> {
                    try {
                        return new HomeResDto(title);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return homeResDtos;
    }
}
