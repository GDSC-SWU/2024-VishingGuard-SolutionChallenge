package com.gdsc_solutionchallenge.backend.domain.result.smishing.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhishingUrl {
    @DocumentId
    private String id;
    @NotNull
    private String institution;
    @NotNull
    private String url;

    @Builder
    public PhishingUrl(String institution, String url){
        this.institution=institution;
        this.url=url;
    }
}
