package com.gdsc_solutionchallenge.backend.domain.result.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FSSOpenApiDto {
    private String atchfileUrl;
    private String atchfileNm;
    @Builder
    public FSSOpenApiDto(String atchfileUrl, String atchfileNm){
        this.atchfileUrl=atchfileUrl;
        this.atchfileNm = atchfileNm;
    }

}
