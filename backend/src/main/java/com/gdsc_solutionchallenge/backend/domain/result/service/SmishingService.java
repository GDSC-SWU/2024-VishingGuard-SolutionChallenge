package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.PhishingUrl;
import com.gdsc_solutionchallenge.backend.domain.result.domain.PhishingUrlRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SmishingService {
    public final SmishingRepository smishingRepository;
    public final PhishingUrlRepository phishingUrlRepository;

    public SmishingResDto isSmishing(SmishingReqDto smishingReqDto) throws Exception {
        boolean urlResult = false; // url 결과
        boolean keywordResult = false; // keyword 결과

        List<Smishing> smishingList = smishingRepository.getAllSmishings();
        List<PhishingUrl> phishingUrlList = phishingUrlRepository.getAllURLS();

        // URL 검사
        for (PhishingUrl phishingUrl : phishingUrlList) {
            if (phishingUrl != null && smishingReqDto.getSmishingScript().contains(phishingUrl.getUrl())) {
                urlResult = true; // 하나라도 일치하는 경우 true로 설정
                break; // 일치하는 경우 반복 중단
            }
        }

        // 키워드 검사
        for (Smishing smishing : smishingList) {
            if (smishing != null && removeSpaces(smishingReqDto.getSmishingScript())
                    .contains(removeSpaces(smishing.getSmishingKeyword()))) {
                keywordResult = true; // 하나라도 일치하는 경우 true로 설정
                break; // 일치하는 경우 반복 중단
            }
        }

        return new SmishingResDto(urlResult, keywordResult);
    }


    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
