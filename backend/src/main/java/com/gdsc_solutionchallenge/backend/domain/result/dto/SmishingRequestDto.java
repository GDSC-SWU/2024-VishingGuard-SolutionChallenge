package com.gdsc_solutionchallenge.backend.domain.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SmishingRequestDto {
    @Schema(description = "피싱 검사할 메시지")
    private String message;
    @Builder
    public SmishingRequestDto(String message){
        this.message=message;
    }
}
