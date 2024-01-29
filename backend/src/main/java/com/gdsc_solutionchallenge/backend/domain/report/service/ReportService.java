package com.gdsc_solutionchallenge.backend.domain.report.service;

import com.gdsc_solutionchallenge.backend.domain.report.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.report.domain.VishingRepository;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportVisReqDto;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportVisResDto;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportSmsResDto;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.db.*;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReportService {
    public final SmishingRepository smishingRepository;
    public final VishingRepository vishingRepository;
    public final VishingKeywordRepository vishingKeywordRepository;


    public List<ReportSmsResDto> smishigReport(Long userId) throws Exception {
        List<Smishing> smishings = smishingRepository.getAllScriptByUserId(userId);
        List<ReportSmsResDto> reportSmsResDtos = smishings.stream()
                .map(smishing -> new ReportSmsResDto(smishing))
                .collect(Collectors.toList());
        return reportSmsResDtos;
    }

    public List<ReportVisResDto> vishingReport(Long userId, ReportVisReqDto reportVisReqDto) throws Exception {
        String keywordComment = null;
        List<VishingKeyword> vishingKeywordList = vishingKeywordRepository.getAllVishingKeyword();

        // 키워드 검사
        for (VishingKeyword keyword : vishingKeywordList) {
            if (keyword != null && removeSpaces(reportVisReqDto.getVishingScript())
                    .contains(removeSpaces(keyword.getVishing_keyword()))) {
                keywordComment = "보이스피싱 위험 키워드가 포함되어있습니다. ("+ keyword.getVishing_keyword() + ")";
                break; // 일치하는 경우 반복 중단
            }else{
                keywordComment = "보이스피싱 위험 키워드가 포함되어있습니다.";
            }
        }

        // SmishingScript Entity 생성
        Vishing vishing = Vishing.builder()
                .script(reportVisReqDto.getVishingScript())
                .phone(reportVisReqDto.getPhone())
                .date(reportVisReqDto.getDate())
                .time(reportVisReqDto.getTime())
                .user_id(userId)
                .keyword_comment(keywordComment)
                .build();

        vishingRepository.save(vishing); // 스크립트 저장

        List<Vishing> vishings = vishingRepository.getAllScriptByUserId(userId);
        List<ReportVisResDto> reportVisResDtos = vishings.stream()
                .map(vishing1 -> new ReportVisResDto(vishing1))
                .collect(Collectors.toList());
        return reportVisResDtos;
    }




    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
