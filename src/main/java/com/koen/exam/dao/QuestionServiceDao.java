package com.koen.exam.dao;

import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.dao.repo.QuestionEntityRepository;
import com.koen.exam.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceDao {
    @Autowired
    QuestionEntityRepository questionEntityRepository;
    public QuestionEntity save(QuestionEntity questionEntity){
        return questionEntityRepository.save(questionEntity);
    }
}
