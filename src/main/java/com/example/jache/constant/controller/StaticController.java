package com.example.jache.constant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

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

    @RequestMapping("/chat/chatPage")
    public String chatPage(){
        return "chat/chatPage";
    }

    @RequestMapping("/receipe/receipe-form")
    public String receipeForm(){
        return "receipe/receipe-form";
    }
}
