package com.example.jache.config;

import com.example.jache.chat.handler.StompExceptionHandler;
import com.example.jache.chat.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final StompHandler stompHandler;
    private final StompExceptionHandler stompExceptionHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //엔드포인트 추가 등록
        registry.addEndpoint("/ws-jache").setAllowedOrigins("http://localhost:8080").withSockJS();
                //.setErrorHandler(stompExceptionHandler)		//exception handler를 위한 것
                //.addEndpoint("/ws-jache")
                //.addInterceptors()
                //.setAllowedOriginPatterns("*")
                //.withSockJS();
    }

    //Client 로 부터 들어오는 메시지를 처리하는 MessageChannel 을 구성하는 역할을 하는 메소드
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        /**
//         *STOMP 메세지 처리를 구성하는 메세지 채널에 custom 한 인터셉터를 추가 구성하여
//         * 채널의 현재 인터셉터 목록에 추가하는 단계를 거칩니다.
//         */
//
//        registration.interceptors(stompHandler);
//    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //해당 파라미터의 접두사가 붙은 목적지(구독자)에 메시지를 보낼
        registry.enableSimpleBroker("/room"); //수신 시 붙이기
        registry.setApplicationDestinationPrefixes("/send");  //송신 시 붙이기
    }
}
