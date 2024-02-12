package com.gdsc_solutionchallenge.backend.domain.report.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.VishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportVisResDto;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.report.dto.ReportSmsResDto;
import com.gdsc_solutionchallenge.backend.domain.report.domain.VishingKeyword;
import com.gdsc_solutionchallenge.backend.domain.report.domain.VishingKeywordRepository;
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
    private final UserRepository userRepository;

    public List<ReportSmsResDto> smishigReport(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        List<Smishing> smishings = smishingRepository.getAllScriptByUserId(userId);
        List<ReportSmsResDto> reportSmsResDtos = smishings.stream()
                .map(smishing -> new ReportSmsResDto(smishing))
                .collect(Collectors.toList());
        return reportSmsResDtos;
    }

    public List<ReportVisResDto> vishingReport(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        List<Vishing> vishings = vishingRepository.getAllScriptByUserId(userId);
        List<ReportVisResDto> reportVisResDtos = vishings.stream()
                .map(vishing -> new ReportVisResDto(vishing))
                .collect(Collectors.toList());
        return reportVisResDtos;
    }

    public String reportState(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        int vishingCount = vishingRepository.getAllScriptByUserId(userId).size();
        int smishingCount = smishingRepository.getAllScriptByUserId(userId).size();

        int totalCount = vishingCount + smishingCount;

        if (totalCount >= 0 && totalCount <= 1) {
            return "Safe";
        } else if (totalCount >= 2 && totalCount <= 3) {
            return "Moderate";
        } else {
            return "Risky";
        }
    }



    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
