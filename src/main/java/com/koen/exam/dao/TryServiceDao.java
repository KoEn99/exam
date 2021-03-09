package com.koen.exam.dao;

import com.koen.exam.dao.entity.TryEntity;
import com.koen.exam.dao.repo.TryEntityRepository;
import com.koen.exam.services.TryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TryServiceDao {
    @Autowired
    TryEntityRepository tryEntityRepository;
    public TryEntity saveTry(TryEntity tryEntity){
        return tryEntityRepository.save(tryEntity);
    }
}
