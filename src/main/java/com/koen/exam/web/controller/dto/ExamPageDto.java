package com.koen.exam.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPageDto {
    private String title;
    private String description;
    private float generalScore;
}
