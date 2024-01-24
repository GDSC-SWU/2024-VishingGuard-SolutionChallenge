package com.gdsc_solutionchallenge.backend.domain.result.dto;

import lombok.Getter;

@Getter
public class SpamNumResDto {
    private boolean result;
    private String name;

    public SpamNumResDto(boolean result, String name) {
        this.result = result;
        this.name = name;
    }
}