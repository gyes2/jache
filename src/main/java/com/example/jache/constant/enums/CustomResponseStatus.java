package com.example.jache.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum CustomResponseStatus {
    //100 성공
    SUCCESS(true,100,"요청에 성공했습니다."),

    //200 bad request
    DUPLICATE_CHEFNAME(false,200,"중복된 아이디입니다."),
    DUPLICATE_EMAIL(false,201,"중복된 이메일입니다."),
    BAD_ATHORIZED(false,202,"잘못된 인증번호입니다."),
    MALFORMED_TOKEN(false,206,"잘못된 형식의 토큰입니다."),
    UNSUPPORTED_TOKEN(false,207,"지원하지 않는 토큰입니다."),
    TOKEN_NOT_FOUND(false,208,"토큰을 찾을 수 없습니다."),
    BAD_JWT(false,206,"잘못된 토큰입니다."),
    AUTHENTICATED_FAILED(false,207,"인증에 실패했습니다"),
    WRONG_INPUT_IMAGE(false,208,"이미지 형식이 잘못되었습니다."),
    ALREADY_LOVE(false,209,"이미 좋아요를 누른 레시피 입니다."),
    ALREADY_UNLOVE(false,211,"이미 좋아요를 취소한 레시피입니다."),
    TOKEN_IS_NULL(false,212,"토큰이 존재하지 않습니다."),
    WRONG_INGREDIENT_ID(false,213,"레시피에 해당하지 않는 재료 아이디입니다."),
    WRONG_ORDERS_ID(false,214,"레시피에 해당하지 않는 주문순서 입니다."),


    //300 unauthorized
    EXPIRED_TOKEN(false,300,"만료된 토큰입니다."),
    UNAUTHORIZED_TOKEN(false,301,"권한이 없습니다."),
    BAD_PASSWORD(false,302,"잘못된 비밀번호입니다."),
    BAD_LOGIN_ID(false,303,"잘못된 아이디입니다."),
    EXPIRED_REFRESH_TOKEN(false,304,"리프레시 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(false,305,"유효하지않은 리프레시 토큰입니다."),

    //400 not found
    USER_NOT_FOUND(false,400,"해당 유저를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(false,401,"이메일을 찾을 수 없습니다."),
    RECEIPE_NOT_FOUND(false,402,"해당 레시피를 찾을 수 없습니다."),
    INGREDIENT_NOT_FOUND(false,403,"해당 재료를 찾을 수 없습니다."),
    ORDERS_NOT_FOUND(false,404,"해당 요리순서를 찾을 수 없습니다."),
    CHATROOM_NOT_FOUND(false,405,"해당 채팅방을 찾을 수 없습니다."),

    //500 기타 에러
    IMAGE_UPLOAD_ERROR(false,500,"이미지 업로드에 실패했습니다."),
    S3_IMG_DELETE_ERROR(false,501,"s3 이미지 삭제에 실패했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private CustomResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
