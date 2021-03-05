package com.koen.exam.web.controller.dto;

import lombok.Data;

import java.util.List;
@Data
public class QuestionList {
    private List<QuestionAnswerDto> questionAnswerDto;
}
