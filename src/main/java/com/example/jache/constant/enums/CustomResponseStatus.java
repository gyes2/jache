package com.example.jache.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomResponseStatus {
    //100 성공
    SUCCESS(true,100,"요청에 성공했습니다."),

    //200 bad request
    DUPLICATE_CHEFNAME(false,200,"중복된 아이디입니다."),
    DUPLICATE_EMAIL(false,201,"중복된 이메일입니다."),
    BAD_ATHORIZED(false,202,"잘못된 인증번호입니다.");

    //300 unauthorized


    //400 not found

    //500 기타 에러

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private CustomResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
