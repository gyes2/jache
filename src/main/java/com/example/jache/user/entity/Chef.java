package com.example.jache.user.entity;

import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.entity.love;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chefId;

    @Column(nullable = false, length = 45)
    private String chefName;

    @Column(nullable = false, length = 45)
    private String password;

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 25)
    private String chefDetail;

    private String chefImgUrl;

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

}
