package com.petproject.cardgame.controller;

import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Controller
public class LobbyController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private GameTableRepository gameTableRepository;

    @GetMapping("/lobby")
    public String getLobbyPage(Principal principal) {

        if (
                !lobbyService.isUserInGame(principal.getName())
                && !lobbyService.isUserInLobby(principal.getName())
        ) {
            return "lobby";
        }
        else {
            return "redirect:";
        }


    }

    @MessageMapping("/get_user_list")
    public void addUserInLobby(Principal principal) {
         sendLobbyInfo();
    }

    @MessageMapping("/start")
//    @SendTo("/topic/greetings")
    public void startGame() {
        lobbyService.startGame();
        sendLobbyInfo();
    }

    @EventListener
    public void userConnectToLobby(SessionConnectEvent event) {
        if (
                !lobbyService.isUserInGame(event.getUser().getName())
                && !lobbyService.isUserInLobby(event.getUser().getName())
        ) {
            lobbyService.addUserInLobby(event.getUser().getName());
            sendLobbyInfo();
        }
    }

    @EventListener
    public void userDisconnectToLobby(SessionDisconnectEvent event) {
        lobbyService.removeUserFromLobby(event.getUser().getName());

        sendLobbyInfo();
    }

    public void sendLobbyInfo() {
        template.convertAndSend(
                "/topic/user_list",
                lobbyService.getGameTable().getLobbyEntity()
        );
    }
}
