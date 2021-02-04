package com.koen.exam.services.Impl;

import com.koen.exam.dao.CoursesServiceDao;
import com.koen.exam.dao.ExamServiceDao;
import com.koen.exam.dao.StatusType;
import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.security.CustomUserDetails;
import com.koen.exam.services.ExamService;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.exception.AccessException;
import com.koen.exam.web.controller.exception.ExamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Calendar;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamServiceDao examServiceDao;
    @Autowired
    CoursesServiceDao coursesServiceDao;
    @Override
    public ExamEntity createExam(ExamDto examDto) throws AccessException {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(examDto.getCoursesEntity()).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        if (coursesEntity.getUserEntity().getLogin().equals(login)) {
            ExamEntity examEntity = new ExamEntity();
            examEntity.setTitle(examDto.getTitle());
            examEntity.setDescription(examDto.getDescription());
            examEntity.setDateStart(ZonedDateTime.now());
            examEntity.setDateStop(ZonedDateTime.now().plusDays(1));
            examEntity.setTimeWatch(examDto.getTimeWatch());
            examEntity.setStatusType(StatusType.valueOf(examDto.getStatusType()));
            examEntity.setCoursesEntity(coursesEntity);
            return examServiceDao.createExam(examEntity);
        } else throw new AccessException();
    }
}
