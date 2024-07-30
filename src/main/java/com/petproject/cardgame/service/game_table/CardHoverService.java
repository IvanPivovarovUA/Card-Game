package com.petproject.cardgame.service.game_table;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.HoverEntity;
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

        if (place.equals('H')) {
            gameTableEntity.setHover(creatCleanHoverEntity());
            gameTableEntity.getHover().setHand(index);

        }
        if (place.equals('T')) {
            gameTableEntity.setHover(creatCleanHoverEntity());
            gameTableEntity.getHover().setTable(index);

        }

        if (place.equals('E')) {

            if (
                    gameTableEntity.getHover().getHand() == -1
                    && gameTableEntity.getHover().getTable() != -1
            ) {
                gameTableEntity.getHover().setEnemy(index);
                gameTableEntity.getHover().setPlayer(false);
            }

        }

        if (place.equals('P')) {

            if (
                    gameTableEntity.getHover().getHand() == -1
                    && gameTableEntity.getHover().getTable() != -1
            ) {
                gameTableEntity.getHover().setEnemy(-1);
                gameTableEntity.getHover().setPlayer(true);
            }

        }

        gameTableRepository.save(gameTableEntity);
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
