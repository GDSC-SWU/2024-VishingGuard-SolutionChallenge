package com.gdsc_solutionchallenge.backend.domain.board.heart.service;

import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Boolean isHeart(String userId, String postId) {
        try {
            User user = userRepository.findById(userId);
            if (user == null) {
                throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
            }
            Post post = postRepository.findById(postId);
            if (post == null) {
                throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
            }

            boolean isHeart = heartRepository.findByUserIdAndPostId(user.getId(),post.getId());
            // 북마크가 되어있다면 삭제
            if (isHeart) {
                bookmarkRepository.deleteByTargetMemberId(memberIdToBookmark);
            } else {
                bookmarkRepository.save(new Bookmark(memberToBookmark, memberToBookmark));
            }
            return ResponseEntity.ok(bookmarkRepository.existsByTargetMemberId(memberIdToBookmark));

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    }
}
