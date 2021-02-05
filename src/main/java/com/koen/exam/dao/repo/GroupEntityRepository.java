package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupEntityRepository extends JpaRepository<GroupEntity, String> {
    GroupEntity findByGroupNameSearch(String groupNameSearch);
}
