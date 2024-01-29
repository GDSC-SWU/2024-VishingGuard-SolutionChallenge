package com.gdsc_solutionchallenge.backend.domain.result.smishing.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.*;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.db.PhishingUrl;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.db.PhishingUrlRepository;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.db.SmishingKeyword;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.db.SmishingKeywordRepository;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SmishingService {
    public final SmishingRepository smishingRepository;
    public final SmishingKeywordRepository smishingKeywordRepository;
    public final PhishingUrlRepository phishingUrlRepository;
    private final UserRepository userRepository;

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

    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
