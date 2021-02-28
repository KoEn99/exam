package com.koen.exam.services.Impl;

import com.koen.exam.dao.CoursesServiceDao;
import com.koen.exam.dao.ExamServiceDao;
import com.koen.exam.dao.StatusType;
import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.security.CustomUserDetails;
import com.koen.exam.services.ExamService;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.exception.AccessException;
import com.koen.exam.web.controller.exception.ExamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamServiceDao examServiceDao;
    @Autowired
    CoursesServiceDao coursesServiceDao;
    @Override
    public ExamDto createExam(ExamDto examDto) throws AccessException {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(examDto.getCoursesEntity()).get();
        ExamEntity examEntity = new ExamEntity();
        examEntity.setTitle(examDto.getTitle());
        examEntity.setDescription(examDto.getDescription());
        examEntity.setTimeWatch(examDto.getTimeWatch());
        examEntity.setStatusType(StatusType.valueOf(examDto.getStatusType()));
        examEntity.setCoursesEntity(coursesEntity);
        examEntity = examServiceDao.createExam(examEntity);
        examDto.setId(examEntity.getId());
        return examDto;
    }

    @Override
    public List<ExamDto> getExamByCourseId(String courseId) {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(courseId).get();
        return coursesEntity.
                getExamEntityList().
                stream().
                map(ExamServiceImpl::examEntityToExamDto).
                collect(Collectors.toList());
    }
    public static ExamDto examEntityToExamDto(ExamEntity examEntity){
        return new ExamDto(
                examEntity.getId(),
                examEntity.getTitle(),
                examEntity.getDescription(),
                examEntity.getTimeWatch(),
                examEntity.getDateStart(),
                examEntity.getDateStop(),
                examEntity.getStatusType().name(),
                examEntity.getCoursesEntity().getId()
        );
    }
}
