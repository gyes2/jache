package com.example.jache.constant.controller;

import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaticController {
    private final ChatService chatService;
    @RequestMapping("/main")
    public String indexPage(){

        return "common/index";
    }

    @RequestMapping("/chef/infoEdit")
    public String infoEdit(){
        return "chef/infoEdit";
    }

    @RequestMapping("/chef/likeReceipe")
    public String likereceipe(){
        return "chef/likeReceipe";
    }

    @RequestMapping("/chef/myList")
    public String myList(){
        return "chef/myList";
    }

    @RequestMapping("/chef/profilePage")
    public String myPage(){
        return "/chef/profilePage";
    }

    @RequestMapping("/receipe/detailReceipe")
    public String detailReceipe(){
        return "/receipe/detailReceipe";
    }

    @RequestMapping("/chat/chat")
    public String chat(){

        return "/chat/chat";
    }

    @GetMapping("/chat/chatPage/{chatRoomId}")
    public String chatPage(Model model, @PathVariable Long chatRoomId){
        List<Chat> chatList = chatService.findAllChatByRoomId(chatRoomId);
        model.addAttribute("roomId",chatRoomId);
        model.addAttribute("chatList",chatList);
        return "/chat/chatPage";
    }

    @RequestMapping("/receipe/receipe-form")
    public String receipeForm(){
        return "/receipe/receipe-form";
    }
}
