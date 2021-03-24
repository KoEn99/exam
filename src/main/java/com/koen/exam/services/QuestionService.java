package com.koen.exam.services;

import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import com.koen.exam.web.controller.dto.QuestionWithDataDto;

import java.util.List;

public interface QuestionService {
    AnswerResponse createQuestion(QuestionAnswerDto questionAnswerDto);
    List<QuestionWithDataDto> getQuestionListByExam(Long examId);
    QuestionAnswerDto getPageQuestion(Long questionId);
}
