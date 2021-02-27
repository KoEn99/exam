package com.koen.exam.dao;

import com.koen.exam.dao.entity.AnswerEntity;
import com.koen.exam.dao.repo.AnswerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceDao {
    @Autowired
    AnswerEntityRepository answerEntityRepository;
    public void saveAll(List<AnswerEntity> answerEntities){
        for (AnswerEntity answerEntity : answerEntities) {
            answerEntityRepository.save(answerEntity);
        }
    }
}