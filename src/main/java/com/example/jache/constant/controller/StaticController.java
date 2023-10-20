package com.example.jache.constant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StaticController {

    @RequestMapping("/main")
    public String indexPage(){
        return "common/index";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "chef/login";
    }

    @RequestMapping("/chef/register")
    public String chefRegister(){
        return "chef/register";
    }
    @RequestMapping("/receipe/register")
    public String receipeRegister(){
        return "receipe/receipe-form";
    }

}
