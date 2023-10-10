package com.example.jache.constant.exception;

import com.example.jache.constant.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    CustomResponseStatus customResponseStatus;
}
