package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.entity.TryEntity;
import com.koen.exam.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TryEntityRepository extends JpaRepository<TryEntity, Long> {
    List<TryEntity> getByUserEntityAndExamEntity(UserEntity userEntity, ExamEntity examEntity);
}
