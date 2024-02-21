package com.gdsc_solutionchallenge.backend.domain.route.domain;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Route {
    @DocumentId
    private String id;
    @NotNull
    private String institution;
    @NotNull
    private double x;
    @NotNull
    private double y;
}
