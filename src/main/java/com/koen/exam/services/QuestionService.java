package com.koen.exam.services;

import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;

public interface QuestionService {
    AnswerResponse createQuestion(QuestionAnswerDto questionAnswerDto);
}
