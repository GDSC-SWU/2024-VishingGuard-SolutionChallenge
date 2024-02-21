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

    /**
     * Determine if a phone number is listed as spam.
     *
     * @param spamNumReqDto DTO containing the phone number to be checked for spam.
     * @return SpamNumResDto containing the result of the spam check.
     * @throws Exception if an error occurs during the spam check.
     */
    public SpamNumResDto isSpamNum(SpamNumReqDto spamNumReqDto) throws Exception {
        List<SpamNumber> spamNumberList = spamNumberRepository.getAllNumbers();

        for (SpamNumber spamNumber : spamNumberList) {
            if (spamNumber != null && removeHyphens(spamNumReqDto.getSpamNumber())
                    .equals(removeHyphens(spamNumber.getNumber()))) {
                int count = spamNumberRepository.updateCount(spamNumber);
                return new SpamNumResDto(true, spamNumber.getName(), count);
            }
        }
        return new SpamNumResDto(false, null, 0);
    }

    // Remove hyphens from the input string
    private String removeHyphens(String input) {
        return input.replaceAll("-", "");
    }
}
