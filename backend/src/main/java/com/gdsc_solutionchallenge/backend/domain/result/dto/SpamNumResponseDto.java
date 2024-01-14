package com.gdsc_solutionchallenge.backend.domain.result.dto;

import com.gdsc_solutionchallenge.backend.domain.result.domain.SpamNumber;
import lombok.Getter;

@Getter
public class SpamNumResponseDto {
    private boolean result;
    private String name;

    public SpamNumResponseDto(boolean result, String name) {
        this.result = result;
        this.name = name;
    }
}