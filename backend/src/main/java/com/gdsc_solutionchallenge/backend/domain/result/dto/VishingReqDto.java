package com.gdsc_solutionchallenge.backend.domain.result.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class VishingReqDto {
    private String vishingScript;
    private String phone;
    private String date;
    private String time;

}
