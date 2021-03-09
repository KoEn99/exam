package com.koen.exam.dao;

import com.koen.exam.dao.entity.AnswerUserEntity;
import com.koen.exam.dao.repo.AnswerUserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAnswerServiceDao {
    @Autowired
    AnswerUserEntityRepository answerUserEntityRepository;
    public void saveAnswerUserService(List<AnswerUserEntity> answerUserEntityList){
        answerUserEntityRepository.saveAll(answerUserEntityList);
    }
}
