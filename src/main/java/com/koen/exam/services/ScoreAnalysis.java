package com.koen.exam.services;

import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.services.Impl.ScoreAnalysisImpl;
import com.koen.exam.web.controller.dto.AnalysisDto;

import java.util.List;

public interface ScoreAnalysis {
    AnalysisDto getScoreAnalysis(QuestionEntity questionEntity);
}
