package com.gdsc_solutionchallenge.backend.domain.spamNumber.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpamNumReqDto {
    @Schema(description = "스팸 검사할 전화번호")
    private String spamNumber;
    @Builder
    public SpamNumReqDto(String spamNumber){
        this.spamNumber=spamNumber;
    }
}
