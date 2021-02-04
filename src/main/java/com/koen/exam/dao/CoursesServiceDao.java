package com.koen.exam.dao;

import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.UserEntity;
import com.koen.exam.dao.repo.CoursesEntityRepository;
import com.koen.exam.dao.repo.UserEntityRepository;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoursesServiceDao {
    @Autowired
    CoursesEntityRepository coursesEntityRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    public CoursesEntity createCourse(CoursesEntity coursesEntity) throws AuthException {
        coursesEntityRepository.save(coursesEntity);
        return coursesEntity;
    }
    public UserEntity getUserEntityByLogin(String login){
        return userEntityRepository.findByLogin(login);
    }
    public Optional<CoursesEntity> getCourseEntity(String id){
        return coursesEntityRepository.findById(id);
    }
}
