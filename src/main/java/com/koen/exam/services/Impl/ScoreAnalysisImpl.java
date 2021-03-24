package com.koen.exam.services.Impl;

import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.services.ScoreAnalysis;
import com.koen.exam.web.controller.dto.AnalysisDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreAnalysisImpl implements ScoreAnalysis {

    @Override
    public AnalysisDto getScoreAnalysis(QuestionEntity questionEntity) {
            int generalSize = questionEntity.getAnswerUserEntities().size();
            Long answerTrueCount =
                    questionEntity.getAnswerUserEntities().
                            stream().filter
                            (answerUserEntity -> answerUserEntity.getCorrectAnswer().equals(true)).count();
            AnalysisDto analysisDto = new AnalysisDto();
            int correctResults = Math.round(((float)answerTrueCount/(float) generalSize)*100);
            if (correctResults >= 90.0 || correctResults <= 10.0){
                analysisDto.setMessage("Процент правильных ответов составляет " + correctResults + "%\n"
                + "Возможно вопрос слшиком простой");
                analysisDto.setColor("RED");
            } else if (correctResults >= 70.0 || correctResults <= 30.0){
                analysisDto.setMessage("Процент правильных ответов составляет " + correctResults + "%\n"
                        + "Обратите внимание на данный вопрос, он превышает");
                analysisDto.setColor("YELLOW");
            } else{
                analysisDto.setMessage("Процент правильных ответов составляет " + correctResults + "%\n"
                        + "Данный вопрос не требует пересмотрения");
                analysisDto.setColor("GREEN");
            }
        return analysisDto;
    }
}
