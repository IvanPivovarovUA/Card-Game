package com.petproject.cardgame.service.game_process.card_use;

import com.petproject.cardgame.data.document.CardOnTableDocument;
import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.document.PlayerDocument;
import com.petproject.cardgame.data.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CardUseService {

    @Autowired
    GameTableRepository gameTableRepository;


    public void addCardInHand() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        addCardInHand(gameTableDocument.getIsFirstPlayerStep());
    }

    public void addCardInHand(boolean isFirstPlayerStep) {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        Card card = Card.randomCard();

        if (isFirstPlayerStep) {
            gameTableDocument.getFirstPlayer().getCardsOnHand().add(card);
        }
        else {
            gameTableDocument.getSecondPlayer().getCardsOnHand().add(card);
        }

        gameTableRepository.save(gameTableDocument);
    }


    public void removeHoverCardFromHand() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        int index = gameTableDocument.getHover().getHand();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        mainPlayer.plusMana(
                -mainPlayer.getCardsOnHand().get(index).getMana()
        );
        mainPlayer.getCardsOnHand().remove(index);

        gameTableRepository.save(gameTableDocument);
    }


    private CardOnTableDocument createCardOnTable(Card card, int id) {
        CardOnTableDocument cardOnTableDocument = new CardOnTableDocument();

        cardOnTableDocument.setType(card);
        cardOnTableDocument.setHp(card.getHp());
        cardOnTableDocument.setPower(card.getPower());

        if (
               card.equals(Card.B)
            || card.equals(Card.R)
            || card.equals(Card.r)
        ) {
            cardOnTableDocument.setCanAttack(true);
        }
        else {
            cardOnTableDocument.setCanAttack(false);
        }

        cardOnTableDocument.setId(id);

        return cardOnTableDocument;
    }

    public void putCardOnTable() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        int id = gameTableDocument.getIdForCards();

        mainPlayer.getCardsOnTable().add(
                createCardOnTable(
                        mainPlayer.getCardsOnHand().get(
                                gameTableDocument.getHover().getHand()
                        ),
                        id
                )
        );
        gameTableRepository.save(gameTableDocument);
    }

    public void attackCard() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        int MainCardId = gameTableDocument.getHover().getTable();
        int WorkCardId = gameTableDocument.getHover().getEnemy();

        CardOnTableDocument MainC;
        CardOnTableDocument WorkC;
        List<CardOnTableDocument> MainCList;
        List<CardOnTableDocument> WorkCList;

        if (gameTableDocument.getIsFirstPlayerStep()) {
            MainC = gameTableDocument.getFirstPlayer().getCardsOnTable().get(MainCardId);
            WorkC = gameTableDocument.getSecondPlayer().getCardsOnTable().get(WorkCardId);
            MainCList = gameTableDocument.getFirstPlayer().getCardsOnTable();
            WorkCList = gameTableDocument.getSecondPlayer().getCardsOnTable();
        }
        else {
            MainC = gameTableDocument.getSecondPlayer().getCardsOnTable().get(MainCardId);
            WorkC = gameTableDocument.getFirstPlayer().getCardsOnTable().get(WorkCardId);
            MainCList = gameTableDocument.getSecondPlayer().getCardsOnTable();
            WorkCList = gameTableDocument.getFirstPlayer().getCardsOnTable();
        }

        if (MainC.getCanAttack()) {
            WorkC.plusHp(
                    -MainC.getPower()
            );
            if (
                    !MainC.getType().name().equals("L")
                    && !MainC.getType().name().equals("l")
                    && !MainC.getType().name().equals("t")

                    && !WorkC.getType().name().equals("L")
                    && !WorkC.getType().name().equals("l")
                    && !WorkC.getType().name().equals("t")
            ) {
                MainC.plusHp(
                        -WorkC.getPower()
                );
            }

            MainC.setCanAttack(false);


            if (MainC.getHp() <= 0) {
                MainCList.remove(MainCardId);
            }
            if (WorkC.getHp() <= 0) {
                WorkCList.remove(WorkCardId);
            }
        }

        gameTableRepository.save(gameTableDocument);
    }


    public void attackPlayer() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        int MainCardId = gameTableDocument.getHover().getTable();

        CardOnTableDocument MainC;
        PlayerDocument WorkP;

        if (gameTableDocument.getIsFirstPlayerStep()) {
            MainC = gameTableDocument.getFirstPlayer().getCardsOnTable().get(MainCardId);
            WorkP = gameTableDocument.getSecondPlayer();
        }
        else {
            MainC = gameTableDocument.getSecondPlayer().getCardsOnTable().get(MainCardId);
            WorkP = gameTableDocument.getFirstPlayer();
        }

        if (MainC.getCanAttack()) {
            WorkP.plusHp(-MainC.getPower());

            MainC.setCanAttack(false);

            if (WorkP.getHp() <= 0) {
                System.out.println("end!!!!!");
            }
        }

        gameTableRepository.save(gameTableDocument);
    }


    public Optional<Card> getCardInHand() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        int index = gameTableDocument.getHover().getHand();
        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        if (
                 index != -1
        ) {
            return Optional.of(
                    mainPlayer.getCardsOnHand().get(index)
            );
        }
        else  {
            return Optional.empty();
        }
    }

}
