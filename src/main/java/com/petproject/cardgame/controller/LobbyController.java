package com.petproject.cardgame.controller;


import com.petproject.cardgame.entity.LobbyEntity;
import com.petproject.cardgame.model.Test;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class LobbyController {

    @Autowired
    LobbyService lobbyService;

    @Autowired
    GameTableRepository gameTableRepository;

    @GetMapping("/game_table")
    public String gameTable() {
        return "lobby";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public LobbyEntity addUserInLobby(Principal principal) {

         return lobbyService.getGameTable().getLobbyEntity();
    }
}
