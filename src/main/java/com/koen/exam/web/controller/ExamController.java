package com.koen.exam.web.controller;

import com.koen.exam.services.ExamService;
import com.koen.exam.web.controller.dto.CoursesDto;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.exception.AccessException;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    ExamService examService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<GenericResponse<?>> createCourse (@Valid @RequestBody ExamDto examDto)
            throws AuthException, AccessException {
        return new ResponseEntity<>(new GenericResponse<>(
                examService.createExam(examDto)), HttpStatus.OK);
    }
}
