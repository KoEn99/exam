package com.koen.exam.web.controller;

import com.koen.exam.services.QuestionService;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PreAuthorize("hasRole('USER') and @access.accessCreateQuestion(principal, #questionAnswerDto.examId)")
    @PostMapping("/create")
    public ResponseEntity<GenericResponse<?>> createExam(@RequestBody QuestionAnswerDto questionAnswerDto) {
        return new ResponseEntity<>(new GenericResponse<>(
                questionService.createQuestion(questionAnswerDto)), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER') and @access.accessCreateQuestion(principal, #examId)")
    @GetMapping("/get/list/{examId}")
    public ResponseEntity<GenericResponse<?>> getListQuestion(@PathVariable Long examId) {
        return new ResponseEntity<>(new GenericResponse<>(
                questionService.getQuestionListByExam(examId)), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER') and @access.accessPageQuestion(principal, #questionId)")
    @GetMapping("/page/{questionId}")
    public ResponseEntity<GenericResponse<?>> getAnswerListByQuestion(@PathVariable Long questionId) {
        return new ResponseEntity<>(new GenericResponse<>(
                questionService.getPageQuestion(questionId)), HttpStatus.OK);
    }
}
