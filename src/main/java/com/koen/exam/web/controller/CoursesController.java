package com.koen.exam.web.controller;

import com.koen.exam.services.CoursesService;
import com.koen.exam.web.controller.dto.AuthDto;
import com.koen.exam.web.controller.dto.CoursesDto;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/course")
public class CoursesController {
    @Autowired
    CoursesService coursesService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<GenericResponse<?>> createCourse  (@Valid @RequestBody CoursesDto coursesDto) throws AuthException {
        return new ResponseEntity<>(new GenericResponse<>(
                coursesService.createCourse(coursesDto)), HttpStatus.OK);
    }
}
