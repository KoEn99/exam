package com.koen.exam.web.controller;

import com.koen.exam.services.GroupStudyService;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.dto.GroupDto;
import com.koen.exam.web.controller.exception.AccessException;
import com.koen.exam.web.controller.exception.AuthException;
import com.koen.exam.web.controller.exception.MySelfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/group")
public class GroupStudyController {
    @Autowired
    GroupStudyService groupStudyService;

    @PreAuthorize("hasRole('USER') and @access.accessJoinGroup(principal, #name)")
    @PostMapping("/join/{name}")
    public ResponseEntity<GenericResponse<?>> createCourse(@PathVariable String name)
            throws MySelfException {
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.joinGroup(name)), HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<GenericResponse<?>> findGroup(@PathVariable String name) {
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.findGroup(name)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/my")
    public ResponseEntity<GenericResponse<?>> getMyGroup() {
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.findAllGroup()), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER') and @access.accessCourse(principal, #nameCourse)")
    @GetMapping("/by/{nameCourse}")
    public ResponseEntity<GenericResponse<?>> getGroupByCourse(@PathVariable String nameCourse) {
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.groupByCourse(nameCourse))  , HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER') and @access.accessUserByGroup(principal, #groupId)")
    @GetMapping("/user/{groupId}")
    public ResponseEntity<GenericResponse<?>> getUserGroup(@PathVariable String groupId) {
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.getUserGroup(groupId)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and @access.accessCourse(principal, #groupDto.coursesEntity.getId())")
    @PostMapping("/add")
    public ResponseEntity<GenericResponse<?>> groupAdd(@Valid @RequestBody GroupDto groupDto) throws AccessException {
        return new ResponseEntity<>(new GenericResponse<>(groupStudyService.addGroup(groupDto)), HttpStatus.OK);
    }
}
