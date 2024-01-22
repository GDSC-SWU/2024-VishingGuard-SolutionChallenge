package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.SpamNumber;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SpamNumberRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SpamNumReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SpamNumResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SpamNumService {
    public final SpamNumberRepository spamNumberRepository;

    public SpamNumResDto isSpamNum(SpamNumReqDto spamNumReqDto) throws Exception {
        List<SpamNumber> spamNumberList = spamNumberRepository.getAllNumbers();

        for (SpamNumber spamNumber : spamNumberList){
            if (spamNumber != null && removeHyphens(spamNumReqDto.getSpamNumber())
                    .equals(removeHyphens(spamNumber.getNumber()))) {
                return new SpamNumResDto(true, spamNumber.getName());
            }
        }
        return new SpamNumResDto(false, null);
    }

    // 하이픈 삭제
    private String removeHyphens(String input) {
        return input.replaceAll("-", "");
    }

}
