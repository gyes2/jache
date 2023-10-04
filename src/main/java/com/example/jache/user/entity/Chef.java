package com.example.jache.user.entity;

import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.entity.love;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chef {
    @Id @GeneratedValue
    private Long chefId;
    private String chefName;
    private String password;
    private String phone;
    private String email;

    @Embedded
    private BaseEntity baseEntity;

    @OneToMany(mappedBy = "chef")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "chef")
    private List<love> loves = new ArrayList<>();

    @OneToMany(mappedBy = "chef")
    private List<Receipe> receipes = new ArrayList<>();

    @OneToMany(mappedBy = "chef")
    private List<Chat> chats = new ArrayList<>();

    @Column(nullable = false)
    private String chefDetail;
    @Column(nullable = false)
    private String chefImgUrl;


}
