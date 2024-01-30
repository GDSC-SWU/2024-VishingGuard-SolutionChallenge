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
import com.gdsc_solutionchallenge.backend.domain.result.domain.db.PhishingUrlRepository;
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
    public final PhishingUrlRepository phishingUrlRepository;
    private final UserRepository userRepository;
    public final VishingRepository vishingRepository;
    public final VishingKeywordRepository vishingKeywordRepository;


    public Boolean isSmishing(Long userId, SmishingReqDto smishingReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));
        //boolean urlResult = false; // url 결과
        boolean keywordResult = false; // keyword 결과
        String keywordComment = null;
        //String urlComment = "스미싱 위험 URL이 포함되어있습니다.";

        List<SmishingKeyword> smishingKeywordList = smishingKeywordRepository.getAllSmishingKeyword();
        //List<PhishingUrl> phishingUrlList = phishingUrlRepository.getAllURLS();

//        // URL 검사
//        for (PhishingUrl phishingUrl : phishingUrlList) {
//            if (phishingUrl != null && smishingReqDto.getSmishingScript()
//                    .contains(phishingUrl.getUrl())) {
//                urlResult = true; // 하나라도 일치하는 경우 true로 설정
//                //urlComment = null;
//                break; // 일치하는 경우 반복 중단
//            }
//        }

        // 키워드 검사
        for (SmishingKeyword keyword : smishingKeywordList) {
            if (keyword != null && removeSpaces(smishingReqDto.getSmishingScript())
                    .contains(removeSpaces(keyword.getSmishing_keyword()))) {
                keywordResult = true; // 하나라도 일치하는 경우 true로 설정
                keywordComment = "스미싱 위험 키워드가 포함되어있습니다. ("+ keyword.getSmishing_keyword() + ")";
                break; // 일치하는 경우 반복 중단
            }
        }

        if (keywordResult) {
            // SmishingScript Entity 생성
            Smishing smishing = Smishing.builder()
                    .user_id(userId)
                    .script(smishingReqDto.getSmishingScript())
                    .phone(smishingReqDto.getPhone())
                    //.url_comment(urlComment)
                    .keyword_comment(keywordComment)
                    .build();

            smishingRepository.save(smishing); // 스크립트 저장
        }

        // 두 결과 중 하나라도 true인 경우 true 반환
        return /*urlResult ||*/ keywordResult;
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
                keywordComment = "보이스피싱 위험 키워드가 포함되어있습니다. ("+ keyword.getVishing_keyword() + ")";
                break; // 일치하는 경우 반복 중단
            }else{
                keywordComment = "보이스피싱 위험 키워드가 포함되어있습니다.";
            }
        }

        // SmishingScript Entity 생성
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
