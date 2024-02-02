package com.gdsc_solutionchallenge.backend.domain.info.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReportPlace {
    @DocumentId
    private String id;
    @NotNull
    private String number;
    @NotNull
    private String place;
    @NotNull
    private int order;
    private String tag;
}