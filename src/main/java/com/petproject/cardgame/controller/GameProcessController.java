package com.petproject.cardgame.controller;

import com.petproject.cardgame.mapper.GameTableMapper;
import com.petproject.cardgame.model.InfoForPlayer;
import com.petproject.cardgame.model.UserHoverCardModel;
import com.petproject.cardgame.service.game_table.CardService;
import com.petproject.cardgame.service.game_table.GameProcessService;
import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import static com.petproject.cardgame.mapper.GameTableMapper.GameTableToInfoForPlayer;

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
    @SendToUser("/queue/game_table_info")
    public void addUserInLobby(Principal principal) {
        sendGameTableInfo();
    }

    public void sendGameTableInfo() {

        template.convertAndSendToUser(
                gameProcessService.getFirstPlayerId(),
                "/queue/game_table_info",

                GameTableToInfoForPlayer(
                        lobbyService.getGameTable(),
                        true
                )
        );

        template.convertAndSendToUser(
                gameProcessService.getSecondPlayerId(),
                "/queue/game_table_info",

                GameTableToInfoForPlayer(
                        lobbyService.getGameTable(),
                        false
                )
        );
    }

    @MessageMapping("/next")
    public void next(Principal principal) {
        if (gameProcessService.isUserHaveExec(principal.getName())) {
            gameProcessService.nextPlayerStep();
            sendGameTableInfo();
        }
    }

    @MessageMapping("/hover")
    public void hoverCard(Principal principal, UserHoverCardModel userHoverCardModel) {

    }



//    @MessageMapping("/put_card")
//    public void useCard(Principal principal, UseCardModel useCardModel) {
//        if (gameProcessService.isUserHaveExec(principal.getName())) {
//            cardService.putCard(useCardModel.getMainCardId(), useCardModel.getWorkCardId());
//            sendGameTableInfo();
//        }
//    }
//
//    @MessageMapping("/card_attack")
//    public void cardAttack(Principal principal, UseCardModel useCardModel) {
//        if (gameProcessService.isUserHaveExec(principal.getName())) {
//            if (useCardModel.getWorkCardId() != null) {
//                cardService.cardAttack(useCardModel.getMainCardId(), useCardModel.getWorkCardId());
//            }
//            else {
//                cardService.playerAttack(useCardModel.getMainCardId());
//            }
//            sendGameTableInfo();
//        }
//    }



}
