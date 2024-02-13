package com.gdsc_solutionchallenge.backend.domain.report.dto;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Vishing;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ReportVisResDto {
    private String phone;
    private String keywordComment;
    private String created_at;

    @Builder
    public ReportVisResDto(Vishing vishing) {
        this.phone = vishing.getPhone();
        this.created_at = formatTimestamp(vishing.getCreated_at());
        this.keywordComment = vishing.getKeyword_comment();
    }

    private String formatTimestamp(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        return null;
    }
}
