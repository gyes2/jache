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
    public List<ChatRoom> findAllRoom(String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        return roomRepository.findAllByChef(chef);
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
     * @param name 방 이름
     */
    public ChatRoom createRoom(String name, String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        return roomRepository.save(ChatRoom.builder()
                .chatRoomName(name)
                        .chef(chef)
                .build());
    }

    /////////////////

    /**
     * 채팅 생성
     * @param roomId 채팅방 id
     * @param sender 보낸이
     * @param message 내용
     */
    public Chat createChat(Long roomId, String sender, String message) {
        ChatRoom room = roomRepository.findByChatRoomId(roomId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.CHATROOM_NOT_FOUND)
        );  //방 찾기 -> 없는 방일 경우 여기서 예외처리
        return chatRepository.save(Chat.createChat(room, sender, message));
    }

    /**
     * 채팅방 채팅내용 불러오기
     * @param roomId 채팅방 id
     */
    public List<Chat> findAllChatByRoomId(Long roomId) {
        ChatRoom room = roomRepository.findByChatRoomId(roomId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.CHATROOM_NOT_FOUND)
        );
        return chatRepository.findAllByChatRoom(room);
    }
}
