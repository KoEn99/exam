package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.AnswerUserEntity;
import com.koen.exam.dao.entity.AnswerUserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerUserEntityRepository extends JpaRepository<AnswerUserEntity, AnswerUserKey> {
}
