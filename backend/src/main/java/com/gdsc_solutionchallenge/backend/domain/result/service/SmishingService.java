package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.PhishingUrl;
import com.gdsc_solutionchallenge.backend.domain.result.domain.PhishingUrlRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingResponseDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SpamNumResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SmishingService {
    public final SmishingRepository smishingRepository;
    public final PhishingUrlRepository phishingUrlRepository;

    public SmishingResponseDto isSmishing(SmishingRequestDto smishingRequestDto) throws Exception {
        boolean urlResult = false; // url 결과
        boolean keywordResult = false; // keyword 결과

        List<Smishing> smishingList = smishingRepository.getAllSmishings();
        List<PhishingUrl> phishingUrlList = phishingUrlRepository.getAllURLS();

        // URL 검사
        for (PhishingUrl phishingUrl : phishingUrlList) {
            if (phishingUrl != null && smishingRequestDto.getSmishingScript().contains(phishingUrl.getUrl())) {
                urlResult = true; // 하나라도 일치하는 경우 true로 설정
                break; // 일치하는 경우 반복 중단
            }
        }

        // 키워드 검사
        for (Smishing smishing : smishingList) {
            if (smishing != null && removeSpaces(smishingRequestDto.getSmishingScript())
                    .contains(removeSpaces(smishing.getSmishingKeyword()))) {
                keywordResult = true; // 하나라도 일치하는 경우 true로 설정
                break; // 일치하는 경우 반복 중단
            }
        }

        return new SmishingResponseDto(urlResult, keywordResult);
    }


    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
