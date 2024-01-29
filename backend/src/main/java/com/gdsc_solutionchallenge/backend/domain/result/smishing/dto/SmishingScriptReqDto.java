package com.gdsc_solutionchallenge.backend.domain.result.smishing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SmishingScriptReqDto {
    @Schema(description = "유저 ID")
    private Long userId;
    @Schema(description = "피싱 검사할 메시지")
    private String smishingScript;
    @Schema(description = "전화번호")
    private String phone;
    @Builder
    public SmishingScriptReqDto(String smishingScript){
        this.smishingScript=smishingScript;
    }
}
