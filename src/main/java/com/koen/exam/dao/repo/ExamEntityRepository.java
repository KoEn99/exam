package com.koen.exam.dao.repo;

import com.koen.exam.dao.StatusType;
import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamEntityRepository extends JpaRepository<ExamEntity, Long> {
    List<ExamEntity> findAllByCoursesEntityAndStatusType(CoursesEntity coursesEntity, StatusType statusType);
}
