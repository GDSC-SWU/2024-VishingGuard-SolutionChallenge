package com.gdsc_solutionchallenge.backend.domain.post.board.service;

import com.gdsc_solutionchallenge.backend.domain.post.board.domain.Board;
import com.gdsc_solutionchallenge.backend.domain.post.board.domain.BoardRepository;
import com.gdsc_solutionchallenge.backend.domain.post.board.dto.BoardReqDto;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 작성
    public String saveBoard(BoardReqDto BoardReqDto) throws Exception {
        User user = userRepository.findByNickname(BoardReqDto.getNickname());

        // boardEntity 생성
        Board board = BoardReqDto.toEntity(user);
        boardRepository.saveBoard(board); // 저장

        return BoardReqDto.toEntity(user).getId();
    }
}
