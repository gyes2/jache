package com.example.jache.chat.entity;

import com.example.jache.chat.service.ChatService;
import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatRoom extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private String chatRoomName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "chefId")
//    private Chef chef;
//
//    @OneToMany(mappedBy = "chatRoom",fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
//    private List<Chat> chats = new ArrayList<>();


    public ChatRoom(String chatRoomName){

        this.chatRoomName = chatRoomName;
    }
}
