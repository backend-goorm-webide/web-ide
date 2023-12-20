package com.example.idea.bussiness.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String login() {
        return "login";
    } // 시작 화면
}

