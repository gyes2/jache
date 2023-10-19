package com.example.jache.chat.handler;

import com.example.jache.security.jwtTokens.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
@Order(Ordered.HIGHEST_PRECEDENCE+99) //구성된 인터셉터들의 우선순위를 지정해주는 어노테이션
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenUtil jwtTokenUtil;

    //publisher 가 send 하기 전에 일어님
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        /**
         * StompHeaderAccessor.wrap으로 message를 감싸면 STOMP의 헤더에 직접 접근할 수 있습니다.
         * 클라이언트에서 첫 연결시 헤더에 token 을 담아주면 인증 절차가 진행
         */
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // websocket 연결시 헤더의 jwt token 유효성 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String authorization = jwtTokenUtil.extractJwt(accessor);

            jwtTokenUtil.validateToken(authorization);
        }
        return message;
    }
}
