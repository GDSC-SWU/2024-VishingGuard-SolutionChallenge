package com.gdsc_solutionchallenge.backend.domain.spamNumber.service;

import com.gdsc_solutionchallenge.backend.domain.spamNumber.domain.SpamNumber;
import com.gdsc_solutionchallenge.backend.domain.spamNumber.domain.SpamNumberRepository;
import com.gdsc_solutionchallenge.backend.domain.spamNumber.dto.SpamNumReqDto;
import com.gdsc_solutionchallenge.backend.domain.spamNumber.dto.SpamNumResDto;
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
