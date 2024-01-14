package com.gdsc_solutionchallenge.backend.domain.result.dto;

import lombok.Getter;

@Getter
public class SmishingResponseDto {
    private boolean urlResult;
    private boolean keywordResult;

    public SmishingResponseDto(boolean urlResult, boolean keywordResult) {
        this.urlResult = urlResult;
        this.keywordResult = keywordResult;
    }
}
