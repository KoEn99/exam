package com.koen.exam.services.Impl;

import com.koen.exam.dao.AnswerServiceDao;
import com.koen.exam.dao.QuestionServiceDao;
import com.koen.exam.dao.TryServiceDao;
import com.koen.exam.dao.entity.AnswerEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.services.TryService;
import com.koen.exam.web.controller.dto.AnswerDto;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import com.koen.exam.web.controller.dto.TryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TryServiceImpl implements TryService {
    @Autowired
    private TryServiceDao tryServiceDao;
    @Autowired
    private QuestionServiceDao questionServiceDao;
    @Autowired
    private AnswerServiceDao answerServiceDao;

    @Override
    public TryDto trySubmit(List<QuestionAnswerDto> questionAnswerDtoList) {
        float generalScore = 0;
        for (QuestionAnswerDto questionAnswerDto:questionAnswerDtoList){
            QuestionEntity questionEntity = questionServiceDao.getQuestion(questionAnswerDto.getId());
            List<AnswerEntity> answerEntities = answerServiceDao.
                    getAnswerEntityByQuestionAndCorrectAnswer(questionEntity, true);
            switch (questionEntity.getQuestionType()){
                case FREE_ANSWER:{
                    if (answerEntities.get(0).getTitle().
                            equals(questionAnswerDto.getAnswers().get(0).getAnswer())){
                        generalScore += questionEntity.getScore();
                    }
                    break;
                }
                case MULTIPLE:{
                    if (questionAnswerDto.getAnswers().size() == answerEntities.size()) {
                        for (int i = 0; i < questionAnswerDto.getAnswers().size(); i++) {
                            if (!questionAnswerDto.getAnswers().get(i).getId().equals(
                                    answerEntities.get(i).getId()
                            )){
                                break;
                            }
                        }
                    }
                    generalScore += questionEntity.getScore();
                    break;
                }
                case SINGLE:{
                    if (answerEntities.get(0).getId().
                            equals(questionAnswerDto.getAnswers().get(0).getId())){
                        generalScore += questionEntity.getScore();
                    }
                    break;
                }
            }
        }
        return new TryDto(generalScore);
    }
    private Boolean isAnswerCorrect(AnswerDto answerDto, AnswerEntity answerEntity){
        if (answerDto.getAnswer().equals(answerEntity.getTitle())){
            return true;
        }
        return false;
    }
}
