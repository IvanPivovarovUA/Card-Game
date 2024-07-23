package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.LobbyEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.model.UseCardModel;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameProcessService {

    @Autowired
    GameTableRepository gameTableRepository;

    public boolean isUserHaveExec(String UserId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        LobbyEntity lobbyEntity = gameTableEntity.getLobbyEntity();

        if (
                lobbyEntity.getFirstPlayerId().equals(UserId)
                && gameTableEntity.getIsFirstPlayerStep()
        ) {
            return true;
        }
        else if (
                lobbyEntity.getSecondPlayerId().equals(UserId)
                && !gameTableEntity.getIsFirstPlayerStep()
        ) {
            return true;
        }
        else {
            return false;
        }
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
        gameTableRepository.save(gameTableEntity);
    }
}
