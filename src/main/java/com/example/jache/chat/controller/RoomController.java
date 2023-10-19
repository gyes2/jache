package com.example.jache.chat.controller;

import com.example.jache.chat.dto.ChatDto;
import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.chat.service.ChatService;
import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final ChatService chatService;

    /**
     * 채팅방만들기
     */
    @PostMapping("/chatroom/add")
    public ResponseEntity<ApiResponse<ChatDto.ChatRoomResDto>> createRoom(
            @RequestBody ChatDto.ChatRoomReqDto request
    ){
        ChatDto.ChatRoomResDto newRoom = chatService.createRoom(request.getChatRoomName());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(newRoom, CustomResponseStatus.SUCCESS));
    }

    /**
     * 채팅방 목록 불러오기
     */
    @GetMapping("/chat/roomlist")
    public ResponseEntity<ApiResponse<List<ChatRoom>>> roomList(){
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ChatRoom> list = chatService.findAllRoom();

        return ResponseEntity.ok().body(ApiResponse.createSuccess(list, CustomResponseStatus.SUCCESS));
    }

    /**
     * 채팅방 나가기(접속 해제)
     */
    @MessageMapping("/chat/out")
    @SendTo("/sub/chat/out")
    public String outRoom(String message){
        return message;
    }

    /**
     * 채팅방 참여하기
     */
    @GetMapping("/chat/{chatRoomId}")
    public ResponseEntity<ApiResponse<List<Chat>>> inRoom(@PathVariable Long chatRoomId){
        List<Chat> chats = chatService.findAllChatByRoomId(chatRoomId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(chats,CustomResponseStatus.SUCCESS));
    }
}
