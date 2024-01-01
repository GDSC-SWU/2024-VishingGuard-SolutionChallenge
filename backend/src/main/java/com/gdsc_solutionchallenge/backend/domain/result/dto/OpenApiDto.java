package com.gdsc_solutionchallenge.backend.domain.result.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OpenApiDto {
    private String atchfileUrl;
    @Builder
    public OpenApiDto(String atchfileUrl){
        this.atchfileUrl=atchfileUrl;
    }

}
