package com.example.jache.constant.dto;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.jache.constant.enums.CustomResponseStatus.SUCCESS;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonPropertyOrder({"isSuccess","code","message","data"})
public class ApiResponse<T> {
    @JsonProperty(("isSuccess"))
    private boolean isSuccess;
    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ApiResponse<T> createSuccess(T data, CustomResponseStatus status) {
        return new ApiResponse<>(true, status.getCode(),status.getMessage(), data);
    }

    public static ApiResponse<String> createSuccessWithNoContent(CustomResponseStatus status) {
        return new ApiResponse<>(true, status.getCode(),status.getMessage(), null);
    }

    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static ApiResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(),"유효하지 않은 데이터입니다.", errors);
    }

    // 예외 발생으로 API 호출 실패시 반환


    private ApiResponse(CustomResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getMessage();
    }


    public static ApiResponse<String> createError(CustomResponseStatus status) {
        return new ApiResponse<>(status.isSuccess(), status.getCode(),status.getMessage(), null);
    }
}
