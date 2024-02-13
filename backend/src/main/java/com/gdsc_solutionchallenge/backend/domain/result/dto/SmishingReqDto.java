package com.gdsc_solutionchallenge.backend.domain.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SmishingReqDto {
    @Schema(description = "피싱 검사할 메시지")
    private String smishingScript;
    @Schema(description = "전화번호")
    private String phone;
    @Builder
    public SmishingReqDto(String smishingScript){
        this.smishingScript=smishingScript;
    }
}
