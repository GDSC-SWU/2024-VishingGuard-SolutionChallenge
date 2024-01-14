package com.gdsc_solutionchallenge.backend.domain.result.common;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final int status;
    private String message;
    private T data;

    // status, message, data 모두 넘겨주는 Response
    public BaseResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
