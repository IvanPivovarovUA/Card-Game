package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class OneClickSpellService {

    @Autowired
    GameTableRepository gameTableRepository;


    public void arrowSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            workPlayer = gameTableEntity.getSecondPlayer();
        }
        else {
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

        gameTableRepository.save(gameTableEntity);

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

        gameTableRepository.save(gameTableEntity);
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

        gameTableRepository.save(gameTableEntity);
    }

    public void ishakSpell() {
        // :)
    }

}
