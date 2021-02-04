package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.GroupStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupStudyRepository extends JpaRepository<GroupStudy, Long> {

}
