package com.gdsc_solutionchallenge.backend.domain.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VishingRequestDto {
    @Schema(description = "피싱 검사할 통화 대본")
    private String vishingScript;
    @Builder
    public VishingRequestDto(String vishingScript){
        this.vishingScript=vishingScript;
    }
}
