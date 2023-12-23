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

    public ResponseEntity<Object> isSmishing(SmishingRequestDto smishingRequestDto){
        try{
            List<Smishing> smishingList = smishingRepository.getAllSmishings();

            for (Smishing smishing : smishingList){
                if (smishing != null && removeSpacesAndLowercase(smishingRequestDto.getSmishingScript())
                        .contains(removeSpacesAndLowercase(smishing.getSmishingKeyword())))
                {
                    return ResponseEntity.ok(true);
                }

            }
            return ResponseEntity.ok(false);
        }catch (Exception e){
            // 예외 발생 시, 클라이언트에게 400 Bad Request 와 함께 예외 메시지를 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String removeSpacesAndLowercase(String input) {
        return input.replaceAll("\\s", "").toLowerCase();
    }
}
