package com.example.jache.chat.repository;

import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.user.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    //List<ChatRoom> findAllByChef(Chef chef);

    @Override
    Optional<ChatRoom> findById(Long aLong);

    Optional<ChatRoom> findByChatRoomId(Long roomId);
}
