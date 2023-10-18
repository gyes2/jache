package com.example.jache.constant.handler;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.entity.ErrorResponseEntity;
import com.example.jache.constant.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
//        log.error(e.getErrorCode().getMessage());
        log.info("handleCustomException실행");
        log.info("e.getCode : "+e.getCustomResponseStatus().getCode());
        log.info("e.getStatus : "+e.getCustomResponseStatus().toString());
        log.info("e.getMessage : "+e.getCustomResponseStatus().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createError(e.getCustomResponseStatus()));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<String>> handleException(CustomException e) {
//        log.error(e.getMessage());
//        log.info("handleException실행");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.createError(e.getCustomResponseStatus()));
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(CustomException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createError(e.getCustomResponseStatus()));
    }
}
