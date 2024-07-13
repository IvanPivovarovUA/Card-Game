package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Random;

public class GameProcessService {

    @Autowired
    GameTableRepository gameTableRepository;


    public void startGame() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        gameTableEntity.setIsGameContinues(true);
        gameTableEntity.setIsFirstPlayerStep(true);


        PlayerEntity playerEntity = new PlayerEntity(
                30,
                10,
                new ArrayList<Card>(),
                new ArrayList<CardOnTableEntity>()
        );
        gameTableEntity.setFirstPlayer(playerEntity);
        gameTableEntity.setSecondPlayer(playerEntity);


        gameTableEntity.getUserLobbyEntity().setWinner("?");

        Random PRNG = new Random();
        int RandomNumber;

        RandomNumber = PRNG.nextInt(gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().size());
        gameTableEntity.getUserLobbyEntity().setFirstPlayerId(
                gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().get(RandomNumber)
        );
        gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().remove(RandomNumber);

        RandomNumber = PRNG.nextInt(gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().size());
        gameTableEntity.getUserLobbyEntity().setSecondPlayerId(
                gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().get(RandomNumber)
        );
        gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().remove(RandomNumber);

    }

    public void nextPlayerStep() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();


        if (gameTableEntity.getIsFirstPlayerStep()) {
            gameTableEntity.setIsFirstPlayerStep(false);
            gameTableEntity.getSecondPlayer().setMana(10);

            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getSecondPlayer().getCardsOnTable()) {
                cardOnTableEntity.setCanAttack(true);
            }
        }
        else {
            gameTableEntity.setIsFirstPlayerStep(true);
            gameTableEntity.getFirstPlayer().setMana(10);

            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getSecondPlayer().getCardsOnTable()) {
                cardOnTableEntity.setCanAttack(true);
            }
        }
    }
}
