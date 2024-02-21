package com.gdsc_solutionchallenge.backend.global.common;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final int status;
    private String message;
    private T data;

    // Response constructor that includes status, message, and data
    public BaseResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Response constructor that includes status and message only
    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Response constructor that includes status and data only
    public BaseResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
