package com.koen.exam.services;

import com.koen.exam.dao.entity.GroupUser;
import com.koen.exam.web.controller.dto.GroupDto;
import com.koen.exam.web.controller.exception.MySelfException;

import java.util.List;

public interface GroupStudyService {
    GroupDto addGroup(GroupDto groupDto);
    GroupDto joinGroup(String groupName) throws MySelfException;
    GroupDto findGroup(String groupName);
    List<GroupDto> findAllGroup();
}
