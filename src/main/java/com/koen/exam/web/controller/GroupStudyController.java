package com.koen.exam.web.controller;

import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.GroupStudy;
import com.koen.exam.dao.entity.UserEntity;
import com.koen.exam.dao.repo.CoursesEntityRepository;
import com.koen.exam.dao.repo.GroupStudyRepository;
import com.koen.exam.dao.repo.UserEntityRepository;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.exception.AccessException;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class GroupStudyController {
    @Autowired
    CoursesEntityRepository coursesEntityRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    GroupStudyRepository groupStudyRepository;
    @PostMapping("/group/add")
    public ResponseEntity<GenericResponse<?>> createCourse (@Valid @RequestBody GroupStudy groupStudy)
            throws AuthException, AccessException {
        Optional<CoursesEntity> coursesEntity = coursesEntityRepository.findById(groupStudy.getCoursesEntity().getId());
        UserEntity userEntity = userEntityRepository.findByLogin(groupStudy.getUserEntity().getLogin());
        groupStudy.setCoursesEntity(coursesEntity.get());
        groupStudy.setUserEntity(userEntity);
   /*     String authId = UUID.randomUUID().toString();
        groupStudy.setId(authId);*/
        groupStudyRepository.save(groupStudy);
        return new ResponseEntity<>(new GenericResponse<>(groupStudy), HttpStatus.OK);
    }
}
