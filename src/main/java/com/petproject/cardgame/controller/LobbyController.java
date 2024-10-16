package com.petproject.cardgame.controller;

import com.petproject.cardgame.data.dto.LobbyInfoDto;
import com.petproject.cardgame.service.LobbyService;
import com.petproject.cardgame.service.game_process.card_use.CardUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
    public void redirectToLobby() {
        template.convertAndSend(
                "/topic/redirect_to_game_table",
                ""
        );
    }

    @GetMapping("/lobby")
    public String getLobbyPage(Principal principal) {
        return "lobby";
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
//            sendLobbyInfo();
            redirectToLobby();
        }
    }

    @EventListener
    public void userConnectToLobby(SessionConnectEvent event) {
        OAuth2AuthenticationToken principal =  (OAuth2AuthenticationToken) event.getUser();

        String userId = principal.getPrincipal().getAttribute("sub").toString();
        String userNickName = principal.getPrincipal().getAttribute("given_name").toString();

        lobbyService.addUserInLobby(userId, userNickName);
        sendLobbyInfo();
    }

    @EventListener
    public void userDisconnectToLobby(SessionDisconnectEvent event) {
        OAuth2AuthenticationToken principal =  (OAuth2AuthenticationToken) event.getUser();

        String userId = principal.getPrincipal().getAttribute("sub").toString();
        String userNickName = principal.getPrincipal().getAttribute("given_name").toString();

        lobbyService.removeUserFromLobby(userId, userNickName);
        sendLobbyInfo();
    }
}
