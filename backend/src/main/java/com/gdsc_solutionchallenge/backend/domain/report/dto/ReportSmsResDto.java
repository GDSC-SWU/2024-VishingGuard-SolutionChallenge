package com.gdsc_solutionchallenge.backend.domain.report.dto;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportSmsResDto {
    private String smishingScript;
    private String phone;
    private String date;
    private String time;
    private String keywordComment;

    @Builder
    public ReportSmsResDto(Smishing smishing) {
        this.smishingScript = smishing.getScript();
        this.phone = smishing.getPhone();
        this.date = smishing.getDate();
        this.time = smishing.getTime();
        this.keywordComment = smishing.getKeyword_comment();
    }
}
