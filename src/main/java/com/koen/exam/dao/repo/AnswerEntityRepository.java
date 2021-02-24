package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {
}
