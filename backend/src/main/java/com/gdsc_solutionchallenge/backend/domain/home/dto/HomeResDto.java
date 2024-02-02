package com.gdsc_solutionchallenge.backend.domain.home.dto;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class HomeResDto {
    private String title;

    @Builder
    public HomeResDto(String title) throws Exception {
        this.title = getTitleSnippet(title);
    }

    private String getTitleSnippet(String title) {
        if (title.length() <= 38) {
            return title;
        } else {
            return title.substring(0, 38);
        }
    }
}
