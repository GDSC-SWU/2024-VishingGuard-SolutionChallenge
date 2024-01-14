package com.gdsc_solutionchallenge.backend.domain.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpamNumRequsetDto {
    @Schema(description = "스팸 검사할 전화번호")
    private String spamNumber;
    @Builder
    public SpamNumRequsetDto(String spamNumber){
        this.spamNumber=spamNumber;
    }
}
