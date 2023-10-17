package com.example.jache.constant.handler;

import com.example.jache.constant.enums.CustomResponseStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //security에서 예외가 발생 후 반환되는 exception을 감지하여 후처리를 수행해주는 인터페이스
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        log.info("exception : " + exception);
//        response.sendRedirect("/exception/entrypoint");

        /**
         * 토큰 없는 경우
         */
        if (exception == null) {
            log.info("토큰 없는 경우");
            response.sendRedirect("/exception/entrypoint/nullToken");
            return;
        }

        /**
         * 토큰 만료된 경우
         */
        if(exception.equals(CustomResponseStatus.EXPIRED_TOKEN.getMessage())) {
            log.info("토큰이 만료된 경우임 !!!");
            response.sendRedirect("/exception/entrypoint/expiredToken");
            return;
        }

        /**
         * 토큰 시그니처가 다른 경우
         */
        if(exception.equals(CustomResponseStatus.BAD_JWT.getMessage())) {
            log.info("이상한 토큰이 들어옴 !!!");
            response.sendRedirect("/exception/entrypoint/badToken");
            return;
        }
    }
}
