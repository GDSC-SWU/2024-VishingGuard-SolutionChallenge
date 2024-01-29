package com.gdsc_solutionchallenge.backend.domain.result.smishing.service;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentResDto;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.*;
import com.gdsc_solutionchallenge.backend.domain.result.smishing.dto.SmishingScriptReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SmishingService {
    public final SmishingScriptRepository smishingScriptRepository;
    public final SmishingRepository smishingRepository;
    public final PhishingUrlRepository phishingUrlRepository;

    public Boolean isSmishing(SmishingScriptReqDto smishingScriptReqDto) throws Exception {
        boolean urlResult = false; // url 결과
        boolean keywordResult = false; // keyword 결과

        List<Smishing> smishingList = smishingRepository.getAllSmishings();
        List<PhishingUrl> phishingUrlList = phishingUrlRepository.getAllURLS();

        // URL 검사
        for (PhishingUrl phishingUrl : phishingUrlList) {
            if (phishingUrl != null && smishingScriptReqDto.getSmishingScript().contains(phishingUrl.getUrl())) {
                urlResult = true; // 하나라도 일치하는 경우 true로 설정
                break; // 일치하는 경우 반복 중단
            }
        }

        // 키워드 검사
        for (Smishing smishing : smishingList) {
            if (smishing != null && removeSpaces(smishingScriptReqDto.getSmishingScript())
                    .contains(removeSpaces(smishing.getSmishingKeyword()))) {
                keywordResult = true; // 하나라도 일치하는 경우 true로 설정
                break; // 일치하는 경우 반복 중단
            }
        }

        // SmishingScript Entity 생성
        SmishingScript smishingScript = SmishingScript.builder()
                .userId(smishingScriptReqDto.getUserId())
                .script(smishingScriptReqDto.getSmishingScript())
                .phone(smishingScriptReqDto.getPhone())
                .keywordResult(keywordResult)
                .urlResult(urlResult)
                .build();

        smishingScriptRepository.save(smishingScript); // 스크립트 저장

        // 두 결과 중 하나라도 true인 경우 true 반환
        return urlResult || keywordResult;
    }

    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
