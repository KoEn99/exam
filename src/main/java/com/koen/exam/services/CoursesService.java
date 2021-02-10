package com.koen.exam.services;

import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.web.controller.dto.CourseResponse;
import com.koen.exam.web.controller.dto.CoursesDto;
import com.koen.exam.web.controller.exception.AuthException;

import java.util.List;
import java.util.Optional;

public interface CoursesService {
    CourseResponse createCourse(CoursesDto coursesDto) throws AuthException;
    List<CourseResponse> getMyCourse();
    CourseResponse findCourse(String id);
}