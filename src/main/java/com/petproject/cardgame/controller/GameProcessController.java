package com.petproject.cardgame.controller;

import com.petproject.cardgame.model.UseCardModel;
import com.petproject.cardgame.service.CardService;
import com.petproject.cardgame.service.GameProcessService;
import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class GameProcessController {

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private GameProcessService gameProcessService;

    @Autowired
    private CardService cardService;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/game_table")
    public String getGameTablePage(Principal principal) {
        if (lobbyService.isUserInGame(principal.getName())) {
            return "game_table";
        }
        else {
            return "redirect:lobby";
        }
    }

    @MessageMapping("/get_game_table_info")
    public void addUserInLobby(Principal principal) {
        sendGameTableInfo();
    }

    public void sendGameTableInfo() {
        template.convertAndSend(
                "/topic/game_table_info",
                lobbyService.getGameTable()
        );

    }

    @MessageMapping("/next")
    public void next(Principal principal) {
        if (gameProcessService.isUserHaveExec(principal.getName())) {
            gameProcessService.nextPlayerStep();
            cardService.addCardInHand();
            sendGameTableInfo();
        }
    }

    @MessageMapping("/put_card")
    public void useCard(Principal principal, UseCardModel useCardModel) {
        if (gameProcessService.isUserHaveExec(principal.getName())) {
            cardService.putCard(useCardModel.getMainCardId(), useCardModel.getWorkCardId());
            sendGameTableInfo();
        }
    }

    @MessageMapping("/card_attack")
    public void cardAttack(Principal principal, UseCardModel useCardModel) {
        if (gameProcessService.isUserHaveExec(principal.getName())) {
            if (useCardModel.getWorkCardId() != null) {
                cardService.cardAttack(useCardModel.getMainCardId(), useCardModel.getWorkCardId());
            }
            else {
                cardService.playerAttack(useCardModel.getMainCardId());
            }
            sendGameTableInfo();
        }
    }



}
