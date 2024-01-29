package com.gdsc_solutionchallenge.backend.domain.report.dto;

import lombok.Getter;

@Getter
public class SmishingResDto {
    private boolean urlResult;
    private boolean keywordResult;

    public SmishingResDto(boolean urlResult, boolean keywordResult) {
        this.urlResult = urlResult;
        this.keywordResult = keywordResult;
    }
}
