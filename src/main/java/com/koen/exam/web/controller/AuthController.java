package com.koen.exam.web.controller;

import com.koen.exam.dao.entity.UserEntity;
import com.koen.exam.security.jwt.JwtProvider;
import com.koen.exam.services.AuthorizationService;
import com.koen.exam.web.controller.dto.AuthDto;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
public class AuthController {
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<?>> registerUser(@Valid @RequestBody AuthDto authDto) {
        return new ResponseEntity<>(new GenericResponse<>(
                authorizationService.createPerson(authDto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<?>> auth(@NotBlank @RequestHeader("Authorization") String authorizationHeader)
            throws Exception {
        return new ResponseEntity<>(new GenericResponse<>(authorizationService.createAuthToken(authorizationHeader)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/get")
    public String getUser() {
        return "Hi user";
    }
}
