package com.koen.exam.services.Impl;

import com.koen.exam.dao.CoursesServiceDao;
import com.koen.exam.dao.GroupStudyDao;
import com.koen.exam.dao.UserServiceDao;
import com.koen.exam.dao.entity.*;
import com.koen.exam.security.CustomUserDetails;
import com.koen.exam.services.GroupStudyService;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.GroupDto;
import com.koen.exam.web.controller.exception.MySelfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Optional<CoursesEntity> coursesEntity = coursesServiceDao.getCourseEntity(groupDto.getCoursesEntity().getId());
        GroupEntity groupEntity = new GroupEntity(groupDto.getName(), coursesEntity.get());
        groupEntity.setId(UUID.randomUUID().toString());
        groupEntity.setGroupNameSearch(groupEntity.getId().substring(0, 4));
        groupStudyDao.save(groupEntity);
        groupDto.setId(groupEntity.getId());
        return groupDto;
    }

    @Override
    public GroupDto joinGroup(String groupName) throws MySelfException {
        GroupEntity groupEntity = groupStudyDao.getGroupEntity(groupName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        UserEntity userEntity = userServiceDao.findByLogin(login);
        if (!groupEntity.getCoursesEntity().getUserEntity().getId().equals(userEntity.getId())) {
            GroupUser groupUser = new GroupUser();
            groupUser.setGroupEntity(groupEntity);
            groupUser.setUserEntity(userEntity);
            groupUser.setId(new GroupStudyKey(groupEntity.getId(), userEntity.getId()));
            groupStudyDao.saveGroupUser(groupUser);
        } else throw new MySelfException("Невозможно вступить в созданную вами группу");
        return new GroupDto(groupEntity.getId(), groupEntity.getName(), new CoursesEntity(
                groupEntity.getCoursesEntity().getId(), groupEntity.getCoursesEntity().getTitle(),
                groupEntity.getCoursesEntity().getDescription(), null, null, null
        ));
    }

    @Override
    public GroupDto findGroup(String groupName) {
        GroupEntity groupEntity = groupStudyDao.getGroupEntity(groupName);
        return new GroupDto(
                groupEntity.getId(),
                groupEntity.getName(),
                new CoursesEntity(
                        groupEntity.getCoursesEntity().getId(), groupEntity.getCoursesEntity().getTitle(),
                        groupEntity.getCoursesEntity().getDescription(), null, null,
                        new UserEntity(groupEntity.getCoursesEntity().getUserEntity().getFirstName(),
                                groupEntity.getCoursesEntity().getUserEntity().getLastName(),
                                groupEntity.getCoursesEntity().getUserEntity().getMiddleName()))
        );
    }

    @Override
    public List<GroupDto> findAllGroup() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        UserEntity userEntity = userServiceDao.findByLogin(login);
        List<GroupUser> groupUser = userEntity.getGroupStudies();
        return groupUser.stream().map(GroupStudyServiceImpl::groupUserToGroupDto).collect(Collectors.toList());
    }
    public static GroupDto groupUserToGroupDto(GroupUser groupUser){
        return new GroupDto(groupUser.getGroupEntity().getId(),
                groupUser.getGroupEntity().getName(), new CoursesEntity(groupUser.getGroupEntity().getCoursesEntity().getId(),
                groupUser.getGroupEntity().getCoursesEntity().getTitle(), groupUser.getGroupEntity().getCoursesEntity().getDescription(),
                null, null, null));
    }
}
