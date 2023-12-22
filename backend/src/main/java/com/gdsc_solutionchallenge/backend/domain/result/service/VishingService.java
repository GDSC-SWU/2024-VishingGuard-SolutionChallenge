package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.gdsc_solutionchallenge.backend.domain.result.domain.MessageRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.VishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingRequestDto;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VishingService {
    public final VishingRepository vishingRepository;

    public ResponseEntity<Object> isVishing(VishingRequestDto vishingRequestDto){
        try{
            List<Vishing> vishingList=vishingRepository.getAllVishings();

            for (Vishing vishing: vishingList){
                if (vishing != null && removeSpacesAndLowercase(vishingRequestDto.getVishingScript())
                        .contains(removeSpacesAndLowercase(vishing.getVishingKeyword())))
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
