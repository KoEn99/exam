package com.koen.exam.services;

import com.koen.exam.dao.entity.GroupUser;
import com.koen.exam.web.controller.dto.GroupDto;
import com.koen.exam.web.controller.dto.UserGroupDto;
import com.koen.exam.web.controller.exception.AccessException;
import com.koen.exam.web.controller.exception.MySelfException;

import java.util.List;

public interface GroupStudyService {
    GroupDto addGroup(GroupDto groupDto) throws AccessException;
    GroupDto joinGroup(String groupName) throws MySelfException;
    GroupDto findGroup(String groupName);
    List<GroupDto> findAllGroup();
    List<GroupDto> groupByCourse(String nameCourse);
    List<UserGroupDto>  getUserGroup(String groupId);
}
