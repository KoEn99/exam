package com.koen.exam.dao;

import com.koen.exam.dao.entity.AnswerEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.dao.repo.AnswerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceDao {
    @Autowired
    AnswerEntityRepository answerEntityRepository;

    public void saveAll(List<AnswerEntity> answerEntities) {
        for (AnswerEntity answerEntity : answerEntities) {
            answerEntityRepository.save(answerEntity);
        }
    }

    public List<AnswerEntity> getAnswerEntityByQuestionAndCorrectAnswer(
            QuestionEntity questionEntity, Boolean correctAnswer
    ) {
        return answerEntityRepository.getAnswerEntitiesByQuestionEntityAndCorrectAnswer(questionEntity, correctAnswer);
    }

    public AnswerEntity getAnswerEntityById(Long id) {
        return answerEntityRepository.findById(id).get();
    }
}
