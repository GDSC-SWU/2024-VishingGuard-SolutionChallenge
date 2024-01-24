package com.gdsc_solutionchallenge.backend.domain.board.heart.dto;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeartResDto {
    private String user_Id; // 좋아요 한 유저의 ID
    private String post_Id; // 좋아요 한 게시물의 ID
    private boolean isHeart;

    @Builder
    public HeartResDto(User user, Post post, boolean isHeart){
        this.user_Id=user.getId();
        this.post_Id=post.getId();
        this.isHeart = isHeart;
    }
}
