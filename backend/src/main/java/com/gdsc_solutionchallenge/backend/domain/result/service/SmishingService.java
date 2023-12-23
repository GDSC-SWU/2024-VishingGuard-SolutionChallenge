package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SmishingService {
    public final SmishingRepository smishingRepository;

    public boolean isSmishing(SmishingRequestDto smishingRequestDto) throws Exception {
        List<Smishing> smishingList = smishingRepository.getAllSmishings();

        for (Smishing smishing : smishingList){
            if (smishing != null && removeSpacesAndLowercase(smishingRequestDto.getSmishingScript())
                    .contains(removeSpacesAndLowercase(smishing.getSmishingKeyword()))) {
                return true;
            }
        }
        return false;
    }

    private String removeSpacesAndLowercase(String input) {
        return input.replaceAll("\\s", "").toLowerCase();
    }
}
