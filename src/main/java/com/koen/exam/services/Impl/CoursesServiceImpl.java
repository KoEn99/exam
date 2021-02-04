package com.koen.exam.services.Impl;

import com.koen.exam.dao.CoursesServiceDao;
import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.repo.CoursesEntityRepository;
import com.koen.exam.security.CustomUserDetails;
import com.koen.exam.services.CoursesService;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.CoursesDto;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CoursesServiceImpl implements CoursesService {
    @Autowired
    CoursesServiceDao coursesServiceDao;
    @Override
    public CoursesEntity createCourse(CoursesDto coursesDto) throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        CoursesEntity coursesEntity = new CoursesEntity();
        coursesEntity.setTitle(coursesDto.getTitle());
        coursesEntity.setDescription(coursesDto.getDescription());
        coursesEntity.setUserEntity(coursesServiceDao.getUserEntityByLogin(login));
        String authId = UUID.randomUUID().toString();
        coursesEntity.setId(authId);
        return coursesServiceDao.createCourse(coursesEntity);
    }
}
