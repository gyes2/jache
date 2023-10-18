package com.example.jache.chat.entity;


import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

    private String sender;

    @Column(nullable = false)
    private String message;


    @Builder
    public Chat(ChatRoom chatRoom, String sender, String message){
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
    }

    public static Chat createChat(ChatRoom room, String sender, String message) {
        return Chat.builder()
                .chatRoom(room)
                .sender(sender)
                .message(message)
                .build();
    }
}
