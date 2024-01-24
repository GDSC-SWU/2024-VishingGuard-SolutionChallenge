package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.VishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VishingService {
    public final VishingRepository vishingRepository;

    public boolean isVishing(VishingReqDto vishingReqDto) throws Exception {
        List<Vishing> vishingList=vishingRepository.getAllVishings();

        for (Vishing vishing: vishingList) {
            if (vishing != null && removeSpacesAndLowercase(vishingReqDto.getVishingScript())
                    .contains(removeSpacesAndLowercase(vishing.getVishingKeyword()))) {
                return true;
            }
        }
        return false;
    }

    private String removeSpacesAndLowercase(String input) {
        return input.replaceAll("\\s", "").toLowerCase();
    }
}
