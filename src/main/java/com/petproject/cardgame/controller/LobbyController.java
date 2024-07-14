package com.petproject.cardgame.controller;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.model.Test;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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
    public Test addUserInLobby() {

//        lobbyService.addUserInLobby();
        String e = lobbyService.getGameTable().getUserLobbyEntity().toString();
        Test t = new Test();
        t.Text = "sfgdfgfsgdfsg";
        t.Text = e;
        return t;
    }


}
