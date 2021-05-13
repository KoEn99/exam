package com.koen.exam.web.controller;

import com.koen.exam.services.StatService;
import com.koen.exam.web.controller.dto.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
public class StatisticController {
    @Autowired
    StatService statService;
    @PreAuthorize("hasRole('USER') and @access.accessUserByGroupId(principal, #id)")
    @GetMapping("/group/{id}/exam/{idExam}")
    public ResponseEntity<GenericResponse<?>> getStatByGroup(@PathVariable String id, @PathVariable Long idExam) {
        return new ResponseEntity<>(new GenericResponse<>(
                statService.getStatList(id, idExam)), HttpStatus.OK);
    }
}
