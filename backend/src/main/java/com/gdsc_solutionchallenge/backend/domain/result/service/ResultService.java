package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.report.domain.VishingKeyword;
import com.gdsc_solutionchallenge.backend.domain.report.domain.VishingKeywordRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.VishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.domain.db.SmishingKeyword;
import com.gdsc_solutionchallenge.backend.domain.result.domain.db.SmishingKeywordRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ResultService {
    public final SmishingRepository smishingRepository;
    public final SmishingKeywordRepository smishingKeywordRepository;
    //public final PhishingUrlRepository phishingUrlRepository;
    private final UserRepository userRepository;
    public final VishingRepository vishingRepository;
    public final VishingKeywordRepository vishingKeywordRepository;


    public Boolean isSmishing(Long userId, SmishingReqDto smishingReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));
        boolean keywordResult = false; // keyword 결과
        String keywordComment = null;

        List<SmishingKeyword> smishingKeywordList = smishingKeywordRepository.getAllSmishingKeyword();

        // 키워드 검사
        for (SmishingKeyword keyword : smishingKeywordList) {
            if (keyword != null && removeSpaces(smishingReqDto.getSmishingScript())
                    .contains(removeSpaces(keyword.getSmishing_keyword()))) {
                keywordResult = true; // 하나라도 일치하는 경우 true로 설정
                keywordComment = "It contains a risk keyword related to smishing. ("+ keyword.getSmishing_keyword() + ")";
                break; // 일치하는 경우 반복 중단
            }
        }

        if (keywordResult) {
            // SmishingScript Entity 생성
            Smishing smishing = Smishing.builder()
                    .user_id(userId)
                    .script(smishingReqDto.getSmishingScript())
                    .phone(smishingReqDto.getPhone())
                    .date(smishingReqDto.getDate())
                    .time(smishingReqDto.getTime())
                    .keyword_comment(keywordComment)
                    .build();

            smishingRepository.save(smishing); // 스크립트 저장
        }

        // 결과 반환 (T/F)
        return keywordResult;
    }
    public void saveVishing(Long userId, VishingReqDto vishingReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        String keywordComment = null;
        List<VishingKeyword> vishingKeywordList = vishingKeywordRepository.getAllVishingKeyword();

        // 키워드 검사
        for (VishingKeyword keyword : vishingKeywordList) {
            if (keyword != null && removeSpaces(vishingReqDto.getVishingScript())
                    .contains(removeSpaces(keyword.getVishing_keyword()))) {
                keywordComment = "Contains a risk keyword related to voice phishing. ("+ keyword.getVishing_keyword() + ")";
                break; // 일치하는 경우 반복 중단
            }else{
                keywordComment = "Contains a risk keyword related to voice phishing.";
            }
        }

        // Vishing Entity 생성
        Vishing vishing = Vishing.builder()
                .script(vishingReqDto.getVishingScript())
                .phone(vishingReqDto.getPhone())
                .date(vishingReqDto.getDate())
                .time(vishingReqDto.getTime())
                .user_id(userId)
                .keyword_comment(keywordComment)
                .build();

        vishingRepository.save(vishing); // 스크립트 저장
    }

    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
