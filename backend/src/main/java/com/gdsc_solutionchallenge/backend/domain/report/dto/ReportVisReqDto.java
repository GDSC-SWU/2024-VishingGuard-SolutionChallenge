package com.gdsc_solutionchallenge.backend.domain.report.dto;

import com.gdsc_solutionchallenge.backend.domain.report.domain.Vishing;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReportVisReqDto {
    private String vishingScript;
    private String phone;
    private String date;
    private String time;

}
