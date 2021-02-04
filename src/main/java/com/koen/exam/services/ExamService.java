package com.koen.exam.services;

import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.exception.AccessException;

public interface ExamService {
    ExamEntity createExam(ExamDto examDto) throws AccessException;
}
