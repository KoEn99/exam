package com.koen.exam.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private String authToken;
    private String refreshToken;
    private String fio;
    private String email;
}
