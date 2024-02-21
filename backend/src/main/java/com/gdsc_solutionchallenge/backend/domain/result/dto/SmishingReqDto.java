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
    @Schema(description = "message to test smishing")
    private String smishingScript;
    @Schema(description = "phone number")
    private String phone;
    @Builder
    public SmishingReqDto(String smishingScript){
        this.smishingScript=smishingScript;
    }
}
