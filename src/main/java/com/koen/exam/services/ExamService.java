package com.koen.exam.services;

import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import com.koen.exam.web.controller.exception.AccessException;

import java.util.List;

public interface ExamService {
    ExamDto createExam(ExamDto examDto) throws AccessException;
    List<ExamDto> getExamByCourseId(String courseId);
    List<QuestionAnswerDto> getExam(Long examId);
    void updateExamEntity(ExamDto examDto);
}
