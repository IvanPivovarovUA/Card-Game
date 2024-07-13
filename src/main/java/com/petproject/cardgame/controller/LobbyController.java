package com.petproject.cardgame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LobbyController {

    @GetMapping("/game_table")
    public String gameTable() {
        return "game_table";
    }

}
