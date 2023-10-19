package com.example.jache.chat.controller;

import com.example.jache.chat.dto.ChatDto;
import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.chat.service.ChatService;
import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    /**
     * 채팅보내기
     */
    @MessageMapping("/{chatRoomId}")
    @SendTo("/room/{chatRoomId}")
    public ChatDto.ChatMessage send(@DestinationVariable Long chatRoomId, ChatDto.ChatMessage message){
        Chat chat = chatService.createChat(chatRoomId,message.getSender(), message.getMessage());
        ChatDto.ChatMessage sendMessage = ChatDto.ChatMessage.builder()
                .roomId(chat.getChatRoom().getChatRoomId())
                .sender(chat.getSender())
                .message(chat.getMessage())
                .sendDate(chat.getCreateDate())
                .build();
        return sendMessage;
    }



}
