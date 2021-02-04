package com.koen.exam.services;

import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.CoursesDto;
import com.koen.exam.web.controller.exception.AuthException;

public interface CoursesService {
    CoursesEntity createCourse(CoursesDto coursesDto) throws AuthException;
}
