package com.petproject.cardgame.controller;

import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardGameController {

    @Autowired
    LobbyService lobbyService;

    @GetMapping("/game_table")
    public String gametable(Authentication authentication) {

        if (lobbyService.isUserInGame()) {
            System.out.println(authentication.getName());
            return "game_table.html";
        }
        else {
            return "redirect /lobby";
        }
    }

    @GetMapping("/lobby")
    public String lobby() {
        return "lobby.html";
    }


}
