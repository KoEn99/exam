package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.CoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesEntityRepository extends JpaRepository<CoursesEntity, String> {
}
