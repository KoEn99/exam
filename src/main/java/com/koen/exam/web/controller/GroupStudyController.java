package com.koen.exam.web.controller;

import com.koen.exam.services.GroupStudyService;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.dto.GroupDto;
import com.koen.exam.web.controller.exception.AccessException;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/group")
public class GroupStudyController {
    @Autowired
    GroupStudyService groupStudyService;
    @PostMapping("/join/{name}")
    public ResponseEntity<GenericResponse<?>> createCourse(@PathVariable String name)
            throws AuthException, AccessException {
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.joinGroup(name)), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<GenericResponse<?>> groupAdd(@Valid @RequestBody GroupDto groupDto){
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.addGroup(groupDto)), HttpStatus.OK);
    }
}
