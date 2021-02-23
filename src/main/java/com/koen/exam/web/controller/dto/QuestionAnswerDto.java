package com.koen.exam.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerDto {
    private String question;
    private String questionType;
    private String examId;
    private List<AnswerDto> answers;
}
