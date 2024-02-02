package com.gdsc_solutionchallenge.backend.domain.report.dto;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportSmsResDto {
    private String smishingScript;
    private String phone;
    //private String urlComment;
    private String keywordComment;

    @Builder
    public ReportSmsResDto(Smishing smishing) {
        this.smishingScript = smishing.getScript();
        this.phone = smishing.getPhone();
        //this.urlComment = smishing.getUrl_comment();
        this.keywordComment = smishing.getKeyword_comment();
    }
}
