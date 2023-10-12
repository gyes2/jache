package com.example.jache.chat.entity;

import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ChatRoom extends BaseEntity{
    @Id @GeneratedValue
    private Long chateRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chefId")
    private Chef chef;

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats = new ArrayList<>();

    private Long chatChefId;

}
