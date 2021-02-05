package com.koen.exam.services;

import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.GroupDto;
import com.koen.exam.web.controller.dto.GroupStudyResponseDto;

public interface GroupStudyService {
    GroupDto addGroup(GroupDto groupDto);
    AnswerResponse joinGroup(String groupName);
}
