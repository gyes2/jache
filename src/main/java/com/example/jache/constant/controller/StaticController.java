package com.example.jache.constant.controller;

import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.chat.service.ChatService;
import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.service.ReceipeService;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.service.ChefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaticController {
    private final ChatService chatService;
    private final ReceipeService receipeService;
    private final ChefService chefService;

    @RequestMapping("/main")
    public String indexPage(){
        return "common/index";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "chef/login";
    }
    @RequestMapping("/register")
    public String register(){

        return "chef/register";
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
    public String myList() {

        return "chef/myList";
    }

    @GetMapping("/chef/profilePage")
    public String profilePage() {

        return "chef/profilePage";
    }

    @RequestMapping("/receipe/detailReceipe")
    public String detailReceipe(){

        return "receipe/detailReceipe";
    }

    @RequestMapping("/chat/chat")
    public String chat(){
        return "chat/chat";
    }

    @GetMapping("/chat/chatPage/{chatRoomId}")
    public String chatPage(Model model, @PathVariable Long chatRoomId){
        List<Chat> chatList = chatService.findAllChatByRoomId(chatRoomId);
        model.addAttribute("roomId",chatRoomId);
        model.addAttribute("chatList",chatList);
        return "/chat/chatPage";
    }

    @RequestMapping("/chef/register")
    public String chefRegister(){
        return "chef/register";
    }
    @RequestMapping("/receipe/register")
    public String receipeRegister(){
        return "receipe/receipe-form";
    }

/*    @RequestMapping("/receipe/main-receipe-form")
    public String mainReceipeForm(@PathVariable Long receipeId, Model model){
        model.addAttribute("receipeId", receipeId);
        return "receipe/main-receipe-form";
    }*/
    @RequestMapping("/receipe/receipe-form/{receipeId}")
    public String mainReceipeForm(@PathVariable Long receipeId, Model model){
        model.addAttribute("receipeId", receipeId);
        return "receipe/receipe-form";
    }
}