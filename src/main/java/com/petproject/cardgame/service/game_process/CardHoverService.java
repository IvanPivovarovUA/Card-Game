package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.HoverEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardHoverService {

    @Autowired
    GameTableRepository gameTableRepository;

    public HoverEntity getHover() {
        return gameTableRepository.findById("1").get().getHover();
    }

    public void hover(Integer index, Character place) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        place = Character.toUpperCase(place);

        PlayerEntity MainPlayer;
        PlayerEntity WorkPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainPlayer = gameTableEntity.getFirstPlayer();
            WorkPlayer = gameTableEntity.getSecondPlayer();
        }
        else {
            MainPlayer = gameTableEntity.getSecondPlayer();
            WorkPlayer = gameTableEntity.getFirstPlayer();
        }

        if (place.equals('H')) {
            if (MainPlayer.getCardsOnHand().get(index).getMana() <= MainPlayer.getMana()) {
                gameTableEntity.setHover(creatCleanHoverEntity());
                gameTableEntity.getHover().setHand(index);
            }
        }

        if (place.equals('T')) {
            if (
                    MainPlayer.getCardsOnTable().get(index).getCanAttack()
            ) {
                gameTableEntity.setHover(creatCleanHoverEntity());
                gameTableEntity.getHover().setTable(index);
            }
        }

        if (place.equals('E')) {
            if (
                    gameTableEntity.getHover().getHand() == -1
                    && gameTableEntity.getHover().getTable() != -1
                    && (
                        !isThereArmorCard(WorkPlayer)
                        || WorkPlayer.getCardsOnTable().get(index).getType().name().equals("W")
                        || WorkPlayer.getCardsOnTable().get(index).getType().name().equals("Z")
                    )
            ) {
                gameTableEntity.getHover().setEnemy(index);
                gameTableEntity.getHover().setPlayer(false);
            }

        }

        if (place.equals('P')) {
            if (
                    gameTableEntity.getHover().getHand() == -1
                    && gameTableEntity.getHover().getTable() != -1
                    && !isThereArmorCard(WorkPlayer)
            ) {
                gameTableEntity.getHover().setEnemy(-1);
                gameTableEntity.getHover().setPlayer(true);
            }
        }

        gameTableRepository.save(gameTableEntity);
    }

    public boolean isThereArmorCard(PlayerEntity WorkPlayer) {
        for (CardOnTableEntity cardOnTableEntity: WorkPlayer.getCardsOnTable()) {
            if (
                    cardOnTableEntity.getType().name().equals("W")
                    || cardOnTableEntity.getType().name().equals("Z")
            ) {
                return true;
            }
        }
        return false;
    }

    public HoverEntity creatCleanHoverEntity() {
        HoverEntity hoverEntity = new HoverEntity();

        hoverEntity.setHand(-1);
        hoverEntity.setTable(-1);
        hoverEntity.setEnemy(-1);
        hoverEntity.setPlayer(false);

        return hoverEntity;
    }


    public void reset() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        gameTableEntity.setHover(creatCleanHoverEntity());
        gameTableRepository.save(gameTableEntity);
    }

}
