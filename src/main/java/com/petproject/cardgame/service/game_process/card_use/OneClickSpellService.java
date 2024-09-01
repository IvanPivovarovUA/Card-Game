package com.petproject.cardgame.service.game_process.card_use;

import com.petproject.cardgame.data.document.CardOnTableDocument;
import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.document.PlayerDocument;
import com.petproject.cardgame.data.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class OneClickSpellService {

    @Autowired
    GameTableRepository gameTableRepository;

    @Autowired
    CardUseService cardUseService;

    public void defaultUnitCard() {
        cardUseService.putCardOnTable();
        cardUseService.droppedHoverCardFromHand();
    }


    public void manOnWar() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        for (CardOnTableDocument cardOnTableDocument : mainPlayer.getCardsOnTable()) {
            cardOnTableDocument.plusPower(1);
        }
        gameTableRepository.save(gameTableDocument);

        cardUseService.putCardOnTable();
        cardUseService.droppedHoverCardFromHand();
    }

    public void arrowSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        PlayerDocument mainPlayer;
        PlayerDocument workPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
            workPlayer = gameTableDocument.getSecondPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
            workPlayer = gameTableDocument.getFirstPlayer();
        }


        for (CardOnTableDocument cardOnTableDocument : workPlayer.getCardsOnTable()) {
            cardOnTableDocument.plusHp(-Card.A.getPower());
        }

//        workPlayer.setCardsOnTable(
//                workPlayer.getCardsOnTable()
//                        .stream()
//                        .filter(c -> c.getHp() > 0)
//                        .toList()
//        );

        workPlayer.plusHp(-Card.A.getPower());
        if (workPlayer.getHp() <= 0) {
            System.out.println("End from arrowSpell!!!!!!");
        }


        mainPlayer.getDroppedCards().add(
                cardUseService.createCardOnTable(Card.A)
        );

        gameTableRepository.save(gameTableDocument);
        cardUseService.droppedHoverCardFromHand();

    }

    public void fireSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        PlayerDocument mainPlayer;
        PlayerDocument workPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
            workPlayer = gameTableDocument.getSecondPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
            workPlayer = gameTableDocument.getFirstPlayer();
        }

        for (CardOnTableDocument cardOnTableDocument : mainPlayer.getCardsOnTable()) {
            cardOnTableDocument.plusHp(-Card.A.getPower());
        }
        for (CardOnTableDocument cardOnTableDocument : workPlayer.getCardsOnTable()) {
            cardOnTableDocument.plusHp(-Card.A.getPower());
        }

//        mainPlayer.setCardsOnTable(
//                mainPlayer.getCardsOnTable()
//                        .stream()
//                        .filter(c -> c.getHp() > 0)
//                        .toList()
//        );
//
//        workPlayer.setCardsOnTable(
//                workPlayer.getCardsOnTable()
//                        .stream()
//                        .filter(c -> c.getHp() > 0)
//                        .toList()
//        );

        mainPlayer.getDroppedCards().add(
                cardUseService.createCardOnTable(Card.F)
        );

        gameTableRepository.save(gameTableDocument);
        cardUseService.droppedHoverCardFromHand();
    }

    public void moneySpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        mainPlayer.plusMana(Card.M.getPower());

        mainPlayer.getDroppedCards().add(
                cardUseService.createCardOnTable(Card.M)
        );

        gameTableRepository.save(gameTableDocument);
        cardUseService.droppedHoverCardFromHand();
    }

    public void ishakSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        mainPlayer.getDroppedCards().add(
                cardUseService.createCardOnTable(Card.I)
        );

        gameTableRepository.save(gameTableDocument);


        for (int i = Card.I.getPower(); i > 0; i--) {
            cardUseService.addCardInHand();
        }
        cardUseService.droppedHoverCardFromHand();
    }

}
