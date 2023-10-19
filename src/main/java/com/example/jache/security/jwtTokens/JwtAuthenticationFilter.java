package com.example.jache.security.jwtTokens;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.service.ChefService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {  //filter는 외부의 요청을 가장 먼저 처리하는 곳


    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 웹소켓 핸드쉐이크 요청은 JWT 검증을 건너뛰기
        if (isWebSocketConnectionRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        //request 헤더에서 토큰전체 추출
        String authorizationHeader = request.getHeader("Authorization");


        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        //String token = authorizationHeader.split(" ")[1];
        String token = jwtTokenUtil.resolveToken(authorizationHeader);
        log.info("jwtAuthentication 내부 token 값: " + token);
        if(!token.isEmpty()){

            try{
                // Jwt Token에서 email 추출
                String loginEmail = jwtTokenUtil.getEmail(token);
                log.info("토큰에서 추출한 이메일: "+loginEmail);
                log.info("securitycontext에 설정된 토큰: "+SecurityContextHolder.getContext().getAuthentication());

                jwtTokenUtil.extractClaims(token);

                if(jwtTokenUtil.getRefreshByChefName(token) != null){
                    log.info("token이 유효한지 검사하는 if 문");
                    //권한 부여
                    Authentication authentication = jwtTokenUtil.getAuthentication(token);
                    log.info("filter get Authorities() : " + authentication.getAuthorities().toString());
                    // 현재 security context에 해당 권한 설정
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                //filterChain.doFilter(request, response);
            }catch (ExpiredJwtException expjwt){
                //만료된 토큰 exception
                request.setAttribute("exception", CustomResponseStatus.EXPIRED_TOKEN.getMessage());

            }catch (JwtException je){
                //인가된 토큰과 다른 토큰 exception
                request.setAttribute("exception", CustomResponseStatus.BAD_JWT.getMessage());
            }
        }else{
            filterChain.doFilter(request,response);
            return;
        }
        filterChain.doFilter(request,response);

    }

    private boolean isWebSocketConnectionRequest(HttpServletRequest request) {
        return "GET".equals(request.getMethod()) &&
                request.getRequestURI().equals("/ws-jache") &&
                "websocket".equals(request.getHeader("Upgrade"));
    }
}
