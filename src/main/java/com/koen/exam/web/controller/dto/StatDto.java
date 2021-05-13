package com.koen.exam.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {
    private String fio;
    private int tryCount;
    private float countCorrectAnswer;
    private int mark;
    private float generalScore;
}
