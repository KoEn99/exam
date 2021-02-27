package com.koen.exam.services;

import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;

import java.util.List;

public interface QuestionService {
    AnswerResponse createQuestion(QuestionAnswerDto questionAnswerDto);
    List<QuestionAnswerDto> getQuestionListByExam(Long examId);
    QuestionAnswerDto getPageQuestion(Long questionId);
}
