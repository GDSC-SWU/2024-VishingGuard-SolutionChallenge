package com.gdsc_solutionchallenge.backend.domain.spamNumber.dto;

import lombok.Getter;

@Getter
public class SpamNumResDto {
    private boolean result;
    private String name;
    private int count;

    public SpamNumResDto(boolean result, String name, int count) {
        this.result = result;
        this.name = name;
        this.count=count;
    }
}