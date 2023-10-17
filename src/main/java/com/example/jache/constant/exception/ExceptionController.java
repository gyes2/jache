package com.example.jache.constant.exception;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/exception")
public class ExceptionController {
    @GetMapping("/entrypoint/nullToken")
    public void nullToken(){
        log.info("token is null");
        throw new CustomException(CustomResponseStatus.TOKEN_IS_NULL);
    }

    @GetMapping("/entrypoint/expiredToken")
    public void expiredToken(){
        log.info("token is expired");
        throw new CustomException(CustomResponseStatus.EXPIRED_TOKEN);
    }

    @GetMapping("/entrypoint/badToken")
    public void badToken(){
        log.info("token is bad");
        throw new CustomException(CustomResponseStatus.BAD_JWT);
    }

    @GetMapping("/accessDenied")
    public void accessDeny(){
        log.info("access is denied");
        throw new CustomException(CustomResponseStatus.AUTHENTICATED_FAILED);
    }

//    @GetMapping("/")
//    public void exception(){
//        log.info("exception");
//        throw new CustomException(CustomResponseStatus.UNSUPPORTED_TOKEN);
//    }
}
