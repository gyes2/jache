package com.example.jache.chat.service;

import com.example.jache.chat.dto.ChatDto;
import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.chat.repository.ChatRepository;
import com.example.jache.chat.repository.ChatRoomRepository;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChefRepository chefRepository;
    private final ChatRoomRepository roomRepository;
    private final ChatRepository chatRepository;

    /**
     * 모든 채팅방 찾기
     */
    public List<ChatRoom> findAllRoom() {

        return roomRepository.findAll();
    }

    /**
     * 특정 채팅방 찾기
     * @param id room_id
     */
    public ChatRoom findRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow();
    }


    /**
     * 채팅방 만들기

     */
    public ChatDto.ChatRoomResDto createRoom(String roomName) {

        ChatRoom room = ChatRoom.builder()
                        .chatRoomName(roomName)
                                .build();
        log.info(room.toString());
        roomRepository.save(room);


        return ChatDto.ChatRoomResDto.builder()
                .chatRoomName(room.getChatRoomName())
                .chatRoomId(room.getChatRoomId())
                .build();
    }

    /////////////////

    /**
     * 채팅 생성
     */
    public Chat createChat(Long roomId, String sender, String message) {
        ChatRoom room = roomRepository.findByChatRoomId(roomId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.CHATROOM_NOT_FOUND)
        );  //방 찾기 -> 없는 방일 경우 여기서 예외처리
        return chatRepository.save(Chat.createChat(room, sender, message));
    }

    /**
     * 채팅방 채팅내용 불러오기
     */
    public List<Chat> findAllChatByRoomId(Long roomId) {
        ChatRoom room = roomRepository.findByChatRoomId(roomId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.CHATROOM_NOT_FOUND)
        );
        return chatRepository.findAllByChatRoom(room);
    }
}
