package com.petproject.cardgame.controller;

import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    LobbyService lobbyService;

    @EventListener
    public void userConnectToLobby(SessionConnectEvent event) {
        System.out.println("11111111111111111111111111");
        lobbyService.addUserInLobby(event.getUser().getName());
        System.out.println(event.getUser().getName());
        sendLobbyInfo();
        System.out.println("2222222222222222222222222222222");
    }

    @EventListener
    public void userDisconnectToLobby(SessionDisconnectEvent event) {
        System.out.println("33333333333333333333333");
        lobbyService.removeUserFromLobby(event.getUser().getName());
        System.out.println(event.getUser().getName());
        sendLobbyInfo();
        System.out.println("444444444444444444444444");
    }

    public void sendLobbyInfo() {
        template.convertAndSend(
                "/topic/greetings",
                lobbyService.getGameTable().getLobbyEntity()
        );
    }
}
