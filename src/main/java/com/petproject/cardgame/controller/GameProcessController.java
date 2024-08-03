package com.petproject.cardgame.controller;

import com.petproject.cardgame.model.UserHoverCardModel;
import com.petproject.cardgame.service.game_process.CardHoverService;
import com.petproject.cardgame.service.game_process.UseCardService;
import com.petproject.cardgame.service.game_process.GameProcessService;
import com.petproject.cardgame.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    private UseCardService useCardService;

    @Autowired
    private CardHoverService cardHoverService;

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
//    @SendToUser("/queue/game_table_info")
    public void addUserInLobby() {
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
            useCardService.addCardInHand();
            cardHoverService.reset();
            sendGameTableInfo();
        }
    }

    @MessageMapping("/hover")
    public void hoverCard(Principal principal, UserHoverCardModel userHoverCardModel) {
        if (gameProcessService.isUserHaveExec(principal.getName())) {
            cardHoverService.hover(
                    userHoverCardModel.getIndex(),
                    userHoverCardModel.getPlace()
            );
            sendGameTableInfo();
        }
    }

    @MessageMapping("/reset")
    public void resetHover(Principal principal) {
        if (gameProcessService.isUserHaveExec(principal.getName())) {
            cardHoverService.reset();
            sendGameTableInfo();
        }
    }

    @MessageMapping("/use_card")
    public void useCard(Principal principal) {

        if (gameProcessService.isUserHaveExec(principal.getName())) {
            Integer index;

            Integer index2;
            Boolean index3;

            index = cardHoverService.getHover().getHand();
            if (index != -1) {
                useCardService.putCardOnTable(index);
            }

            index = cardHoverService.getHover().getTable();
            if (index != -1) {
                index2 = cardHoverService.getHover().getEnemy();
                if (index2 != -1) {
                    useCardService.attackCard(index, index2);
                }

                index3 = cardHoverService.getHover().getPlayer();
                if (index3) {
                    useCardService.attackPlayer(index);
                }
            }

            cardHoverService.reset();
            sendGameTableInfo();
        }
    }
}
