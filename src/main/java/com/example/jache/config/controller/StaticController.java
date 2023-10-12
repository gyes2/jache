package com.example.jache.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StaticController {
    @RequestMapping("/")
    public String ttPage(){
        return "common/tt";
    }

    @RequestMapping("/main")
    public String indexPage(){
        return "common/index";
    }

}
