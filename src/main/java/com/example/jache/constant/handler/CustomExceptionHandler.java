package com.example.jache.constant.handler;

import com.example.jache.constant.entity.ErrorResponseEntity;
import com.example.jache.constant.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handelCustomException(CustomException e){
        return ErrorResponseEntity.toErrorResponseEntity(e.getCustomResponseStatus());
    }
}
