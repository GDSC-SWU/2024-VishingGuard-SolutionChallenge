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
public class SmishingScript {
    @DocumentId
    String id;
    @NotNull
    Long userId;
    @NotNull
    String script;
    @NotNull
    String phone;
    @NotNull
    boolean urlResult;
    @NotNull
    boolean keywordResult;
    @Builder
    public SmishingScript(String script, Long userId, String phone, boolean urlResult, boolean keywordResult){
        this.userId=userId;
        this.script=script;
        this.phone=phone;
        this.urlResult=urlResult;
        this.keywordResult=keywordResult;
    }
}
