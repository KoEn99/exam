package com.koen.exam.web.controller.exception;

import com.koen.exam.web.controller.dto.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    static int AUTH_EXCEPTION = 1000;
    static int ACCESS_EXCEPTION = 2000;

    @Autowired
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for(FieldError fE : fieldErrors){
            stringBuilder.append(fE.getField()).append(" : ");
            if(fE.getDefaultMessage()!=null){
                stringBuilder.append(fE.getDefaultMessage()).append("\n");
            } else {
                stringBuilder.append(fE.getCode()).append("\n");
            }
        }
        log.error("ERROR: method arguments are not valid: ", stringBuilder.toString());
        GenericResponse<?> genericResponse = new GenericResponse(1000,stringBuilder.toString());
        return new ResponseEntity(genericResponse, BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<GenericResponse<?>> handleAuthException(AuthException e) {
        GenericResponse<?> genericResponse = new GenericResponse<>(AUTH_EXCEPTION, e.getMessage());
        return new ResponseEntity<>(genericResponse, UNAUTHORIZED);
    }
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccessException.class)
    protected ResponseEntity<GenericResponse<?>> handleAccessException(AccessException e) {
        GenericResponse<?> genericResponse = new GenericResponse<>(ACCESS_EXCEPTION, e.getMessage());
        return new ResponseEntity<>(genericResponse, BAD_REQUEST);
    }
}
