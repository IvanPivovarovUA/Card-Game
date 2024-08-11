package com.petproject.cardgame.service.game_process.card_use;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
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
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        addCardInHand(gameTableEntity.getIsFirstPlayerStep());
    }

    public void addCardInHand(boolean isFirstPlayerStep) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        if (isFirstPlayerStep) {
            gameTableEntity.getFirstPlayer().getCardsOnHand().add(Card.randomCard());
        }
        else {
            gameTableEntity.getSecondPlayer().getCardsOnHand().add(Card.randomCard());
        }

        gameTableRepository.save(gameTableEntity);
    }


    public void removeHoverCardFromHand() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        int index = gameTableEntity.getHover().getHand();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        mainPlayer.plusMana(
                -mainPlayer.getCardsOnHand().get(index).getMana()
        );
        mainPlayer.getCardsOnHand().remove(index);

        gameTableRepository.save(gameTableEntity);
    }


    private CardOnTableEntity createCardOnTable(Card card) {
        CardOnTableEntity cardOnTableEntity = new CardOnTableEntity();

        cardOnTableEntity.setType(card);
        cardOnTableEntity.setHp(card.getHp());
        cardOnTableEntity.setPower(card.getPower());

        if (
               card.equals(Card.B)
            || card.equals(Card.R)
            || card.equals(Card.r)
        ) {
            cardOnTableEntity.setCanAttack(true);
        }
        else {
            cardOnTableEntity.setCanAttack(false);
        }

        return cardOnTableEntity;
    }

    public void putCardOnTable() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        mainPlayer.getCardsOnTable().add(
                createCardOnTable(
                        mainPlayer.getCardsOnHand().get(
                                gameTableEntity.getHover().getHand()
                        )
                )
        );
        gameTableRepository.save(gameTableEntity);
    }

    public void attackCard() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int MainCardId = gameTableEntity.getHover().getTable();
        int WorkCardId = gameTableEntity.getHover().getEnemy();

        CardOnTableEntity MainC;
        CardOnTableEntity WorkC;
        List<CardOnTableEntity> MainCList;
        List<CardOnTableEntity> WorkCList;

        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(MainCardId);
            WorkC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(WorkCardId);
            MainCList = gameTableEntity.getFirstPlayer().getCardsOnTable();
            WorkCList = gameTableEntity.getSecondPlayer().getCardsOnTable();
        }
        else {
            MainC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(MainCardId);
            WorkC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(WorkCardId);
            MainCList = gameTableEntity.getSecondPlayer().getCardsOnTable();
            WorkCList = gameTableEntity.getFirstPlayer().getCardsOnTable();
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

        gameTableRepository.save(gameTableEntity);
    }


    public void attackPlayer() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int MainCardId = gameTableEntity.getHover().getTable();

        CardOnTableEntity MainC;
        PlayerEntity WorkP;

        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(MainCardId);
            WorkP = gameTableEntity.getSecondPlayer();
        }
        else {
            MainC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(MainCardId);
            WorkP = gameTableEntity.getFirstPlayer();
        }

        if (MainC.getCanAttack()) {
            WorkP.plusHp(-MainC.getPower());

            MainC.setCanAttack(false);

            if (WorkP.getHp() <= 0) {
                System.out.println("end!!!!!");
            }
        }

        gameTableRepository.save(gameTableEntity);
    }


    public Optional<Card> getCardInHand() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        int index = gameTableEntity.getHover().getHand();
        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
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
