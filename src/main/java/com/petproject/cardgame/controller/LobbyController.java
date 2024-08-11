package com.petproject.cardgame.controller;

import com.petproject.cardgame.service.LobbyService;
import com.petproject.cardgame.service.game_process.card_use.CardUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

import static com.petproject.cardgame.mapper.LobbyMapper.gametTableToLobbyInfoDto;

@Controller
public class LobbyController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private CardUseService cardUseService;

    public void sendLobbyInfo() {
        template.convertAndSend(
                "/topic/user_list",
                gametTableToLobbyInfoDto(lobbyService.getGameTable())
        );
    }

    @GetMapping("/lobby")
    public String getLobbyPage(Principal principal) {

        if (lobbyService.isUserInGame(principal.getName())) {
            return "redirect:game_table";
        }
        else if (lobbyService.isUserInLobby(principal.getName())) {
            return "redirect:";
        } else {
            return "lobby";
        }

    }

    @MessageMapping("/get_lobby_info")
    public void addUserInLobby(Principal principal) {
         sendLobbyInfo();
    }

    @MessageMapping("/start")
    public void startGame() {
        if (lobbyService.canIStartGame()) {
            lobbyService.startGame();

            for (int i = 0; i < 6; i++) {
                cardUseService.addCardInHand(true);
            }
            for (int i = 0; i < 6; i++) {
                cardUseService.addCardInHand(false);
            }

            sendLobbyInfo();
        }
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
}
