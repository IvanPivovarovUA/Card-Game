package com.petproject.cardgame.mapper;


import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.model.InfoForPlayer;


public class GameTableMapper {

    static public InfoForPlayer GameTableToInfoForPlayer(GameTableEntity gameTableEntity, boolean isItInfoForFirstPlayer) {
        InfoForPlayer infoForPlayer = new InfoForPlayer();

        if (isItInfoForFirstPlayer) {
            infoForPlayer.setYourHand(
                    gameTableEntity.getFirstPlayer().getCardsOnHand()
            );

            infoForPlayer.setYourTable(
                    gameTableEntity.getFirstPlayer().getCardsOnTable()
            );

            infoForPlayer.setEnemyTable(
                    gameTableEntity.getSecondPlayer().getCardsOnTable()
            );

            infoForPlayer.setEnemyHand(
                    gameTableEntity.getSecondPlayer().getCardsOnHand().size()
            );


            infoForPlayer.setIsYourStep(gameTableEntity.getIsFirstPlayerStep());

            infoForPlayer.setYourNickname(gameTableEntity.getLobby().getFirstPlayerId());
            infoForPlayer.setEnemyNickname(gameTableEntity.getLobby().getSecondPlayerId());
        }
        else {
            infoForPlayer.setYourHand(
                    gameTableEntity.getSecondPlayer().getCardsOnHand()
            );

            infoForPlayer.setYourTable(
                    gameTableEntity.getSecondPlayer().getCardsOnTable()
            );

            infoForPlayer.setEnemyTable(
                    gameTableEntity.getFirstPlayer().getCardsOnTable()
            );

            infoForPlayer.setEnemyHand(
                    gameTableEntity.getFirstPlayer().getCardsOnHand().size()
            );

            infoForPlayer.setIsYourStep(!gameTableEntity.getIsFirstPlayerStep());

            infoForPlayer.setYourNickname(gameTableEntity.getLobby().getSecondPlayerId());
            infoForPlayer.setEnemyNickname(gameTableEntity.getLobby().getFirstPlayerId());
        }

        return infoForPlayer;
    }
}
