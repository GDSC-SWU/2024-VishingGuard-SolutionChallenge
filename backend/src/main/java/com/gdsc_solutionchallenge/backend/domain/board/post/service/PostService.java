package com.gdsc_solutionchallenge.backend.domain.board.post.service;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostListResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResDto savePost(String userId, PostReqDto postReqDto) throws Exception {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        // boardEntity 생성
        Post post = postReqDto.toEntity(user);
        postRepository.save(post);

        // 저장된 엔티티에서 ID를 가져와 반환
        return new PostResDto(post,null);
    }

    public PostResDto updatePost(String userId, String postId, PostUpdateReqDto postUpdateReqDto) throws Exception {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }
        //System.out.println(post.getUser().getNickname());
        User user= userRepository.findById(userId);
        if(user==null){
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "user not found");
        } else if (!user.getNickname().equals(post.getUser().getNickname())){
            throw new BaseException(HttpStatus.FORBIDDEN.value(), "no permission to modify");
        }

        post.update(postUpdateReqDto.getTitle(), postUpdateReqDto.getContent());
        postRepository.update(post);

        return new PostResDto(post, true);
    }

    public PostResDto findPostById (String userId, String postId) throws Exception {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        User user= userRepository.findById(userId);
        if(user == null){
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "user not found");
        } else if (!user.getNickname().equals(post.getUser().getNickname())){
            return new PostResDto(post, false);
        }

        return new PostResDto(post, true);
    }

    public List<PostListResDto> getAllPosts() throws Exception {
        return postRepository.getAll().stream()
                // BoardRepository 의 findAllDesc 메서드를 호출하여 게시글을 내림차순으로 조회 (쿼리 기능)
                .map(PostListResDto::new)// 각 게시글을 BoardListDto 로 변환
                .collect(Collectors.toList()); // 이후 리스트로 수집하여 반환
    }

    public String deletePost(String userId, String postId) throws Exception {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        User user= userRepository.findById(userId);
        if(user == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "user not found");
        } else if (!user.getNickname().equals(post.getUser().getNickname())){
            throw new BaseException(HttpStatus.FORBIDDEN.value(), "no permission to delete");
        }

        return postRepository.delete(postId);
    }
}
