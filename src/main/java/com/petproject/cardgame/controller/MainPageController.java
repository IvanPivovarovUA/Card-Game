package com.petproject.cardgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainPageController {
    @GetMapping("/")
    public String getLobbyPage(Principal principal) {
        return "main_page.html";
    }

}
