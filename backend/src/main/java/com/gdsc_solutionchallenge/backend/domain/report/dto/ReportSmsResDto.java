package com.gdsc_solutionchallenge.backend.domain.report.dto;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.google.cloud.Timestamp;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ReportSmsResDto {
    private String smishingScript;
    private String phone;
    private String created_at;
    private String keywordComment;

    @Builder
    public ReportSmsResDto(Smishing smishing) {
        this.smishingScript = smishing.getScript();
        this.phone = smishing.getPhone();
        this.created_at = formatTimestamp(smishing.getCreated_at());
        this.keywordComment = smishing.getKeyword_comment();
    }
    private String formatTimestamp(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        return null;
    }
}
