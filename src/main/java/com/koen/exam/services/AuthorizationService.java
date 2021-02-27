package com.koen.exam.services;

import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.AuthDto;
import com.koen.exam.web.controller.dto.Token;

import javax.security.auth.message.AuthException;

public interface AuthorizationService {
    Token createAuthToken(String authorization) throws AuthException, com.koen.exam.web.controller.exception.AuthException;

    AnswerResponse createPerson(AuthDto authDto) throws com.koen.exam.web.controller.exception.AuthException;
}