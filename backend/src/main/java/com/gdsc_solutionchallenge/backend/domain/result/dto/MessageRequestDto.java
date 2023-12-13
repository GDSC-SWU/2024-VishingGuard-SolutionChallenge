package com.gdsc_solutionchallenge.backend.domain.result.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MessageRequestDto {
    private String message;
    @Builder
    public MessageRequestDto(String message){
        this.message=message;
    }
}
