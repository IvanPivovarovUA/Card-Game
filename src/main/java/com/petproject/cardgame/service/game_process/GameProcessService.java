package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameProcessService {

    @Autowired
    GameTableRepository gameTableRepository;


    public String getFirstPlayerId() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        return gameTableEntity.getFirstPlayer().getId();
    }

    public String getSecondPlayerId() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        return gameTableEntity.getSecondPlayer().getId();
    }

    public boolean isUserHaveExec(String UserId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        if (
                gameTableEntity.getFirstPlayer().getId().equals(UserId)
                && gameTableEntity.getIsFirstPlayerStep()
        ) {
            return true;
        }
        else if (
                gameTableEntity.getSecondPlayer().getId().equals(UserId)
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
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(true);
                }
            }
            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getFirstPlayer().getCardsOnTable()) {
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(false);
                }
            }
        }
        else {
            gameTableEntity.setIsFirstPlayerStep(true);
            gameTableEntity.getFirstPlayer().setMana(10);
            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getFirstPlayer().getCardsOnTable()) {
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(true);
                }
            }
            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getSecondPlayer().getCardsOnTable()) {
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(false);
                }
            }

        }
        gameTableRepository.save(gameTableEntity);
    }
}
