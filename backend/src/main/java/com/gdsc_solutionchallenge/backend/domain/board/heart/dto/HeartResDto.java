package com.gdsc_solutionchallenge.backend.domain.board.heart.dto;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeartResDto {
    private Long user_Id; // ID of the user who liked
    private String post_Id; // ID of the post that was liked
    private boolean isHeart;

    @Builder
    public HeartResDto(User user, Post post, boolean isHeart){
        this.user_Id=user.getId();
        this.post_Id=post.getId();
        this.isHeart = isHeart;
    }
}
