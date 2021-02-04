package com.koen.exam.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private int errorCode;
    private String errorMessage;
    T responseData;

    public GenericResponse(T responseData) {
        this.errorCode = 0;
        this.responseData = responseData;
    }

    public GenericResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
