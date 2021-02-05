package com.koen.exam.services.Impl;

import com.koen.exam.dao.CoursesServiceDao;
import com.koen.exam.dao.GroupStudyDao;
import com.koen.exam.dao.UserServiceDao;
import com.koen.exam.dao.entity.*;
import com.koen.exam.security.CustomUserDetails;
import com.koen.exam.services.GroupStudyService;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.GroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GroupStudyServiceImpl implements GroupStudyService {
    @Autowired
    CoursesServiceDao coursesServiceDao;
    @Autowired
    GroupStudyDao groupStudyDao;
    @Autowired
    UserServiceDao userServiceDao;

    @Override
    public GroupDto addGroup(GroupDto groupDto) {
        Optional<CoursesEntity> coursesEntity = coursesServiceDao.getCourseEntity(groupDto.getIdCourse());
        GroupEntity groupEntity = new GroupEntity(groupDto.getName(), coursesEntity.get());
        groupEntity.setId(UUID.randomUUID().toString());
        groupEntity.setGroupNameSearch(groupEntity.getId().substring(0, 4));
        groupStudyDao.save(groupEntity);
        groupDto.setId(groupEntity.getId());
        return groupDto;
    }

    @Override
    public AnswerResponse joinGroup(String groupName) {
        GroupEntity groupEntity = groupStudyDao.getGroupEntity(groupName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        UserEntity userEntity = userServiceDao.findByLogin(login);
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupEntity(groupEntity);
        groupUser.setUserEntity(userEntity);
        groupUser.setId(new GroupStudyKey(groupEntity.getId(), userEntity.getId()));
        groupStudyDao.saveGroupUser(groupUser);
        return new AnswerResponse("Вы вступили в группу");
    }
}
