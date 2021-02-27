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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    ExamService examService;
    @PreAuthorize("hasRole('USER') and @access.accessCourse(principal, #examDto.coursesEntity)")
    @PostMapping("/create")
    public ResponseEntity<GenericResponse<?>> createExam (@Valid @RequestBody ExamDto examDto)
            throws AccessException {
        return new ResponseEntity<>(new GenericResponse<>(
                examService.createExam(examDto)), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER') and @access.accessCourse(principal, #courseId)")
    @GetMapping("/get/list/{courseId}")
    public ResponseEntity<GenericResponse<?>> getListExam (@PathVariable String courseId) {
        return new ResponseEntity<>(new GenericResponse<>(
                examService.getExamByCourseId(courseId)), HttpStatus.OK);
    }
}
