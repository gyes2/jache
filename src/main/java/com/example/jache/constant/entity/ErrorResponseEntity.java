package com.example.jache.constant.entity;

import ch.qos.logback.core.spi.ErrorCodes;
import com.example.jache.constant.enums.CustomResponseStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    private boolean isSuccess;
    private int code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toErrorResponseEntity(CustomResponseStatus e){
        return ResponseEntity.status(e.getCode())
                .body(ErrorResponseEntity.builder()
                        .isSuccess(e.isSuccess())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
