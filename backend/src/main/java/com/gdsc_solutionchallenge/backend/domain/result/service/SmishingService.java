package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.PhishingUrl;
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

    public SmishingResponseDto isSmishing(SmishingRequestDto smishingRequestDto) throws Exception {
        boolean urlResult; //url 결과
        boolean keywordResult; //keyword 결과
        List<Smishing> smishingList = smishingRepository.getAllSmishings();
        List<PhishingUrl> phishingUrlList = smishingRepository.getAllSmishings();

        for (Smishing smishing : smishingList) {
            if (smishing != null && removeSpacesAndLowercase(smishingRequestDto.getSmishingScript())
                    .contains(removeSpacesAndLowercase(smishing.getSmishingKeyword()))) {
                keywordResult = true;
            } else {
                keywordResult = false;
            }
        }
        for (PhishingUrl phishingUrl :phishingUrlList) {
            if (smishing != null && smishingRequestDto.getSmishingScript()
                    .equals(smishing.getSmishingKeyword())) {
                keywordResult=true;
            }else{
                keywordResult=false;
            }
        }


        }

    }

    private String removeSpacesAndLowercase(String input) {
        return input.replaceAll("\\s", "").toLowerCase();
    }
}
