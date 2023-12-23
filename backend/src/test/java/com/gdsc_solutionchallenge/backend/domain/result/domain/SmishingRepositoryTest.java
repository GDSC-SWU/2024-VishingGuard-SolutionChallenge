package com.gdsc_solutionchallenge.backend.domain.result.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SmishingRepositoryTest {
    @Autowired
    private SmishingRepository smishingRepository;

    @Test
    @DisplayName("스미싱 문구 저장 & 불러오기")
    public void saveText_Load() throws Exception{
        final String text="smishing";
        //Given
        Smishing savedSmishing = smishingRepository.saveMessage(Smishing.builder()
                .smishingKeyword(text)
                .build());
        // When
        Smishing smishing = smishingRepository.findById(savedSmishing.getId());

        // Then
        assertThat(smishing.getSmishingKeyword()).isEqualTo(savedSmishing.getSmishingKeyword());
    }

}