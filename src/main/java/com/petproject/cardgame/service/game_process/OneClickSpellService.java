package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.game_process.card_use.CardUseService;
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
        cardUseService.removeHoverCardFromHand();
    }


    public void manOnWar() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        for (CardOnTableEntity cardOnTableEntity : mainPlayer.getCardsOnTable()) {
            cardOnTableEntity.plusPower(1);
        }
        gameTableRepository.save(gameTableEntity);

        cardUseService.putCardOnTable();
        cardUseService.removeHoverCardFromHand();
    }

    public void arrowSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity mainPlayer;
        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
            workPlayer = gameTableEntity.getSecondPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
            workPlayer = gameTableEntity.getFirstPlayer();
        }


        for (CardOnTableEntity cardOnTableEntity : workPlayer.getCardsOnTable()) {
            cardOnTableEntity.plusHp(-Card.A.getPower());
        }

        workPlayer.setCardsOnTable(
                workPlayer.getCardsOnTable()
                        .stream()
                        .filter(c -> c.getHp() > 0)
                        .toList()
        );

        workPlayer.plusHp(-Card.A.getPower());
        if (workPlayer.getHp() <= 0) {
            System.out.println("End from arrowSpell!!!!!!");
        }

        mainPlayer.getDropedSpells().add(Card.A);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();

    }

    public void fireSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity mainPlayer;
        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
            workPlayer = gameTableEntity.getSecondPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
            workPlayer = gameTableEntity.getFirstPlayer();
        }

        for (CardOnTableEntity cardOnTableEntity : mainPlayer.getCardsOnTable()) {
            cardOnTableEntity.plusHp(-Card.A.getPower());
        }
        for (CardOnTableEntity cardOnTableEntity : workPlayer.getCardsOnTable()) {
            cardOnTableEntity.plusHp(-Card.A.getPower());
        }

        mainPlayer.setCardsOnTable(
                mainPlayer.getCardsOnTable()
                        .stream()
                        .filter(c -> c.getHp() > 0)
                        .toList()
        );

        workPlayer.setCardsOnTable(
                workPlayer.getCardsOnTable()
                        .stream()
                        .filter(c -> c.getHp() > 0)
                        .toList()
        );

        mainPlayer.getDropedSpells().add(Card.F);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();
    }

    public void moneySpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        mainPlayer.plusMana(Card.M.getPower());

        mainPlayer.getDropedSpells().add(Card.M);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();
    }

    public void ishakSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }
        mainPlayer.getDropedSpells().add(Card.I);
        gameTableRepository.save(gameTableEntity);


        for (int i = Card.I.getPower(); i > 0; i--) {
            cardUseService.addCardInHand();
        }
        cardUseService.removeHoverCardFromHand();
    }

}
