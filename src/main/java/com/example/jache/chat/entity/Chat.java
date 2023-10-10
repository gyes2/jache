package com.example.jache.chat.entity;

import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;

@Entity
public class Chat extends BaseEntity {
    @Id @GeneratedValue
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chateRoomId")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chefId")
    private Chef chef;

    @Column(nullable = false)
    private String content;

}
