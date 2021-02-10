package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.GroupStudyKey;
import com.koen.exam.dao.entity.GroupUser;
import com.koen.exam.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupStudyRepository extends JpaRepository<GroupUser, GroupStudyKey> {
    List<GroupUser> findAllByUserEntity(UserEntity userEntity);
}
