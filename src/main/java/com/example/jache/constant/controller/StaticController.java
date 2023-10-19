package com.example.jache.constant.controller;

import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.service.ReceipeService;
import com.example.jache.receipe.service.impl.ReceipeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("/main")
    public String indexPage(){

        return "common/index";
    }

    @RequestMapping("/login")
    public String login(){

        return "common/login";
    }

    @RequestMapping("/register")
    public String register(){

        return "common/register";
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

    @RequestMapping("/receipe/main-receipe-form")
    public String mainReceipeForm(@PathVariable Long receipeId, Model model){
        model.addAttribute("receipeId", receipeId);
        return "receipe/main-receipe-form";
    }
/*    @RequestMapping("/receipe/main-receipe-form/{receipeId}")
    public String mainReceipeForm(@PathVariable Long receipeId, Model model){
        model.addAttribute("receipeId", receipeId);
        return "receipe/main-receipe-form";
    }*/
}