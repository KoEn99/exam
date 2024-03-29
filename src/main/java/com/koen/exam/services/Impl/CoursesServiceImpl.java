package com.koen.exam.services.Impl;

import com.koen.exam.dao.CoursesServiceDao;
import com.koen.exam.dao.ExamServiceDao;
import com.koen.exam.dao.StatusType;
import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.entity.UserEntity;
import com.koen.exam.security.CustomUserDetails;
import com.koen.exam.services.CoursesService;
import com.koen.exam.web.controller.dto.CoursePageDto;
import com.koen.exam.web.controller.dto.CourseResponse;
import com.koen.exam.web.controller.dto.CoursesDto;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CoursesServiceImpl implements CoursesService {
    @Autowired
    CoursesServiceDao coursesServiceDao;
    @Autowired
    ExamServiceDao examServiceDao;
    @Override
    public CourseResponse createCourse(CoursesDto coursesDto) throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        CoursesEntity coursesEntity = new CoursesEntity();
        coursesEntity.setTitle(coursesDto.getTitle());
        coursesEntity.setDescription(coursesDto.getDescription());
        coursesEntity.setUserEntity(coursesServiceDao.getUserEntityByLogin(login));
        String authId = UUID.randomUUID().toString();
        coursesEntity.setId(authId);
        coursesServiceDao.createCourse(coursesEntity);
        return new CourseResponse(authId, coursesDto.getTitle(), coursesDto.getDescription());
    }

    @Override
    public List<CourseResponse> getMyCourse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        UserEntity userEntity = coursesServiceDao.getUserEntityByLogin(login);
        List<CoursesEntity> coursesEntities = userEntity.getCoursesEntityList();
        return coursesEntities.stream().map(CoursesServiceImpl::courseEntityToDto).collect(Collectors.toList());
    }
    public static CourseResponse courseEntityToDto(CoursesEntity coursesEntity){
        return new CourseResponse(coursesEntity.getId(),
                coursesEntity.getTitle(), coursesEntity.getDescription());
    }

    @Override
    public CoursePageDto findCourse(String id) {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(id).get();
        List<ExamEntity> examEntity = examServiceDao.findAllByCoursesEntityAndStatusType(coursesEntity, StatusType.ACTIVE);
        return new CoursePageDto(
                coursesEntity.getId(),
                coursesEntity.getTitle(),
                coursesEntity.getDescription(),
                examEntity.
                        stream().
                        map(ExamServiceImpl::examEntityToExamDto).
                        collect(Collectors.toList())
        );
    }
}
