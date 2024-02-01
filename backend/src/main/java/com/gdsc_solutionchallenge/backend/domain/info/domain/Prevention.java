package com.gdsc_solutionchallenge.backend.domain.info.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Prevention {
    @DocumentId
    private String id;
    @NotNull
    private String title;
    @NotNull
    private String content;

}
