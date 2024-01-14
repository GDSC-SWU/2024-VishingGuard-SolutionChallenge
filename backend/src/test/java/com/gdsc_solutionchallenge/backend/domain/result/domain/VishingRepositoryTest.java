package com.gdsc_solutionchallenge.backend.domain.result.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class VishingRepositoryTest {
    @Autowired
    private VishingRepository vishingRepository;

    @Test
    @DisplayName("보이스 피싱 문구 저장 & 불러오기")
    public void saveText_Load() throws Exception{
        final String text="vishing";
        //Given
        Vishing savedVishing = vishingRepository.saveVishing(Vishing.builder()
                .vishingKeyword(text)
                .build());
        // When
        Vishing vishing = vishingRepository.findById(savedVishing.getId());

        // Then
        assertThat(vishing.getVishingKeyword()).isEqualTo(savedVishing.getVishingKeyword());
    }
}