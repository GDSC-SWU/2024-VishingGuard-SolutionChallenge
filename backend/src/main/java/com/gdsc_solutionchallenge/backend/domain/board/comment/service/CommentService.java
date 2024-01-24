package com.gdsc_solutionchallenge.backend.domain.board.comment.service;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.CommentRepository;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostListResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
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
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentResDto saveComment(String userId, String postId, CommentReqDto commentReqDto) throws Exception {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }
        // Comment Entity 생성
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(commentReqDto.getContent())
                .build();

        commentRepository.save(comment);

        if (user.getNickname().equals(post.getUser().getNickname())){
            return new CommentResDto(comment, post,true);
        }else{
            return new CommentResDto(comment, post,false);
        }
    }

//    public PostResDto updatePost(String id, PostUpdateReqDto postUpdateReqDto) throws Exception {
//        Post post = postRepository.findById(id);
//        if (post == null) {
//            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
//        }
//
//        post.update(postUpdateReqDto.getTitle(), postUpdateReqDto.getContent());
//
//        postRepository.save(post);
//        return new PostResDto(post);
//    }
//
//    public Post findPostById (String id) throws Exception {
//        // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
//        Post post = postRepository.findById(id);
//        if (post == null) {
//            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
//        }
//
//        return post;
//    }
//
//    public List<PostListResDto> getAllPosts() throws Exception {
//        return postRepository.getAll().stream()
//                // BoardRepository 의 findAllDesc 메서드를 호출하여 게시글을 내림차순으로 조회 (쿼리 기능)
//                .map(PostListResDto::new)// 각 게시글을 BoardListDto 로 변환
//                .collect(Collectors.toList()); // 이후 리스트로 수집하여 반환
//    }
//
//    public String deletePost(String id) throws Exception {
//        // boardRepository 에서 주어진 id에 해당하는 게시글을 데이터베이스에서 조회
//        Post post = postRepository.findById(id);
//        if (post == null) {
//            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
//        }
//
//        return postRepository.delete(id);
//    }
}
