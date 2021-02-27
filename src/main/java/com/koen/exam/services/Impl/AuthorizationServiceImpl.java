package com.koen.exam.services.Impl;

import com.koen.exam.dao.UserServiceDao;
import com.koen.exam.dao.entity.UserEntity;
import com.koen.exam.security.jwt.JwtProvider;
import com.koen.exam.services.AuthorizationService;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.AuthDto;
import com.koen.exam.web.controller.dto.GenericResponse;
import com.koen.exam.web.controller.dto.Token;
import com.koen.exam.web.controller.exception.AuthException;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Override
    public Token createAuthToken(String authorization) throws AuthException {
        authorization = cutAuthCode(authorization);
        Map<String, String> loginAndPasswordMap = getLoginAndPassword(authorization);
        String login = loginAndPasswordMap.get("login");
        String password = loginAndPasswordMap.get("password");
        UserEntity userEntity = userServiceDao.findByLogin(login);
        if (userEntity == null) throw new AuthException("Пользователь с таким Email не существует");
        else if (!checkPasswordUser(password, userEntity)) throw new AuthException("Неверный логин или пароль");
        return new Token(jwtProvider.generateToken(userEntity.getLogin()), null);
    }
    private String cutAuthCode(String authorization) throws AuthException {
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith("Basic")) {
            authorization = authorization.substring(6);
            if (StringUtils.isBlank(authorization)) {
                throw new AuthException("Пустые данные авторизации");
            }
        } else {
            throw new AuthException("Пустые данные авторизации");
        }
        return authorization;
    }
    private Map<String, String> getLoginAndPassword(String authHeader) throws BadCredentialsException {
            String customerCredentials = (convertFromBase64(authHeader));
            String login = customerCredentials.split(":")[0];
            String password = customerCredentials.split(":")[1];
            Map<String, String> loginAndPasswordMap = new HashMap<>();
            loginAndPasswordMap.put("login", login);
            loginAndPasswordMap.put("password", password);
            return loginAndPasswordMap;
    }
    private Boolean checkPasswordUser(String password, UserEntity userEntity) throws AuthException{
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            return true;
        } else return false;
    }
    private String convertFromBase64(String base64String) {
        byte[] bytes = base64String.getBytes(StandardCharsets.UTF_8);
        bytes = Base64.getDecoder().decode(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    @Override
    public AnswerResponse createPerson(AuthDto authDto) throws AuthException {
        if (userServiceDao.findByLogin(authDto.getLogin()) == null) {
            userServiceDao.saveUser(authDto);
        } else throw new AuthException("Пользователь с данной электронной почтой существует");
        return new AnswerResponse("Регистрация прошла успешно");
    }
}
