package com.petproject.cardgame.controller;

import com.petproject.cardgame.service.GameTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardGameController {

    @Autowired
    GameTableService gameTableService;

    @GetMapping("/game_table")
    protected String gametable(Authentication authentication) {

        if (gameTableService.isUserInGame()) {
            System.out.println(authentication.getName());
            return "game_table.html";
        }
        else {
            return "lobby.html";
        }
    }

}
