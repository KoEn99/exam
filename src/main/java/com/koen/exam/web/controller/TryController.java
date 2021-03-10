package com.koen.exam.web.controller;

import com.koen.exam.services.TryService;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.dto.QuestionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/try")
public class TryController {
    @Autowired
    private TryService tryService;

    @PreAuthorize("hasRole('USER') and @access.accessSubmit(principal, #questionList.questionAnswerDto.get(0).id)")
    @PostMapping("/submit")
    public ResponseEntity<GenericResponse<?>> createExam(@RequestBody QuestionList questionList) {
        return new ResponseEntity<>(new GenericResponse<>(
                tryService.trySubmit(questionList.getQuestionAnswerDto())), HttpStatus.OK);
    }
}
