package com.koen.exam.services;

import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import com.koen.exam.web.controller.dto.TryDto;

import java.util.List;

public interface TryService {
    TryDto trySubmit(List<QuestionAnswerDto> questionAnswerDtoList);
}
