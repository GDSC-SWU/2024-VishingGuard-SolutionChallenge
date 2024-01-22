package com.gdsc_solutionchallenge.backend.domain.board.heart.dto;

import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.Heart;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeartReqDto {
    private String user_Id; // 좋아요 한 유저의 ID
    private String post_Id; // 좋아요 한 게시물의 ID

    //HeartReqDto 생성자
    @Builder
    public HeartReqDto(User user, Post post){
        this.user_Id=user.getId();
        this.post_Id=post.getId();
    }
    public Heart toEntity(String userId, String postId){
        Heart build = Heart.builder()
                .user_id(userId)
                .post_id(postId)
                .build();
        return build;
    }
}
