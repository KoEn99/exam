package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamEntityRepository extends JpaRepository<ExamEntity, Long> {
}
