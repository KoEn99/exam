package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, Long> {
}
