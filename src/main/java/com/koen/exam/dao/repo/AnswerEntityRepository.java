package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.AnswerEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> getAnswerEntitiesByQuestionEntityAndCorrectAnswer(
            QuestionEntity questionEntity, Boolean correctAnswer
    );
}
