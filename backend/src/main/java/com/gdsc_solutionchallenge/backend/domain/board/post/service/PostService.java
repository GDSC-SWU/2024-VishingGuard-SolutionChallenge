package com.gdsc_solutionchallenge.backend.domain.board.post.service;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostListResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReadResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 작성
    public String savePost(PostReqDto postReqDto) throws Exception {
        User user = userRepository.findByNickname(postReqDto.getNickname());
        if (user == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        // boardEntity 생성
        Post post = postReqDto.toEntity(user);
        Post savedPost = postRepository.save(post);

        // 저장된 엔티티에서 ID를 가져와 반환
        return savedPost.getId();
    }

    public String updatePost(String id, PostUpdateReqDto postUpdateReqDto) throws Exception {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        post.update(postUpdateReqDto.getTitle(), postUpdateReqDto.getContent());

        Post updatedPost = postRepository.update(post);

        return updatedPost.getId();

    }

    public Post findPostById (String id) throws Exception {
        // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        return post;
    }

    public List<PostListResDto> getAllPosts() throws Exception {
        return postRepository.getAll().stream()
                // BoardRepository 의 findAllDesc 메서드를 호출하여 게시글을 내림차순으로 조회 (쿼리 기능)
                .map(PostListResDto::new)// 각 게시글을 BoardListDto 로 변환
                .collect(Collectors.toList()); // 이후 리스트로 수집하여 반환
    }

    public String deletePost(String id) throws Exception {
        // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        return postRepository.delete(id);
    }
}
