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
    private final SmishingRepository smishingRepository;
    private final VishingRepository vishingRepository;
    private final UserRepository userRepository;

    /**
     * Generates a phishing report for SMS based on user ID.
     *
     * @param userId User ID for phishing report.
     * @return List of ReportSmsResDto containing SMS phishing report details.
     * @throws Exception if the user is not found.
     */
    public List<ReportSmsResDto> smishingReport(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        List<Smishing> smishings = smishingRepository.getAllScriptByUserId(userId);
        List<ReportSmsResDto> reportSmsResDtos = smishings.stream()
                .map(ReportSmsResDto::new)
                .collect(Collectors.toList());
        return reportSmsResDtos;
    }

    /**
     * Generates a phishing report for vishing (phone calls) based on user ID.
     *
     * @param userId User ID for phishing report.
     * @return List of ReportVisResDto containing vishing report details.
     * @throws Exception if the user is not found.
     */
    public List<ReportVisResDto> vishingReport(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        List<Vishing> vishings = vishingRepository.getAllScriptByUserId(userId);
        List<ReportVisResDto> reportVisResDtos = vishings.stream()
                .map(ReportVisResDto::new)
                .collect(Collectors.toList());
        return reportVisResDtos;
    }

    /**
     * Retrieves the phishing report state based on user ID.
     *
     * @param userId User ID for phishing report.
     * @return String indicating the phishing report state (Safe, Moderate, or Risky).
     * @throws Exception if the user is not found.
     */
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
}
