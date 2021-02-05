package com.koen.exam.dao;

import com.koen.exam.dao.entity.GroupEntity;
import com.koen.exam.dao.entity.GroupUser;
import com.koen.exam.dao.repo.GroupEntityRepository;
import com.koen.exam.dao.repo.GroupStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupStudyDao {
    @Autowired
    GroupEntityRepository groupEntityRepository;
    @Autowired
    GroupStudyRepository groupStudyRepository;
    public void save(GroupEntity groupEntity){
        groupEntityRepository.save(groupEntity);
    }
    public void saveGroupUser(GroupUser groupUser){
        groupStudyRepository.save(groupUser);
    }
    public GroupEntity getGroupEntity(String groupNameSearch){
        return groupEntityRepository.findByGroupNameSearch(groupNameSearch);
    }
}
