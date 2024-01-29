package com.gdsc_solutionchallenge.backend.domain.report.dto;

import com.gdsc_solutionchallenge.backend.domain.report.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.Smishing;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportVisResDto {
    private String phone;
    private String date;
    private String time;
    private String keywordComment;

    @Builder
    public ReportVisResDto(Vishing vishing) {
        this.phone = vishing.getPhone();
        this.date = vishing.getDate();
        this.time = vishing.getTime();
        this.keywordComment = vishing.getKeyword_comment();
    }
}
