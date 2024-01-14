package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SpamNumber;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SpamNumberRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SpamNumRequsetDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SpamNumResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SpamNumService {
    public final SpamNumberRepository spamNumberRepository;

    public boolean isSpamNum(SpamNumRequsetDto spamNumRequsetDto) throws Exception {
        List<SpamNumber> spamNumberList = spamNumberRepository.getAllNumbers();

        for (SpamNumber spamNumber : spamNumberList){
            if (spamNumber != null && removeHyphens(spamNumRequsetDto.getSpamNumber())
                    .contains(removeHyphens(spamNumber.getNumber()))) {
                return new SpamNumResponseDto(true, spamNumber.getName());
            }
        }
        return new SpamNumResponseDto(false, null);
    }

    // 하이픈 삭제
    private String removeHyphens(String input) {
        return input.replaceAll("-", "");
    }

}
