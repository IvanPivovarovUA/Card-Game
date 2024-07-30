package com.petproject.cardgame.mapper;


import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GameTableMapper {

    static public InfoForPlayer GameTableToInfoForPlayer(GameTableEntity gameTableEntity, boolean isItInfoForFirstPlayer) {
        InfoForPlayer infoForPlayer = new InfoForPlayer();
        infoForPlayer.setYourInfo(new YourInfo());
        infoForPlayer.setEnemyInfo(new EnemyInfo());

        if (isItInfoForFirstPlayer) {
            List<CardStats> cardStatsList =
                    gameTableEntity.getFirstPlayer().getCardsOnHand()
                        .stream()
                        .map(
                            c -> new CardStats(c)
                        )
                        .collect(
                                Collectors.toList()
                        );

            infoForPlayer.getYourInfo().setHand(
                    cardStatsList
            );

            infoForPlayer.getYourInfo().setTable(
                    gameTableEntity.getFirstPlayer().getCardsOnTable()
            );

            infoForPlayer.getEnemyInfo().setTable(
                    gameTableEntity.getSecondPlayer().getCardsOnTable()
            );

            infoForPlayer.getEnemyInfo().setHand(
                    gameTableEntity.getSecondPlayer().getCardsOnHand().size()
            );

            infoForPlayer.setIsYourStep(gameTableEntity.getIsFirstPlayerStep());


            infoForPlayer.getYourInfo().setNickname(gameTableEntity.getLobby().getFirstPlayerId());
            infoForPlayer.getEnemyInfo().setNickname(gameTableEntity.getLobby().getSecondPlayerId());


            infoForPlayer.getYourInfo().setHp(gameTableEntity.getFirstPlayer().getHp());
            infoForPlayer.getYourInfo().setMana(gameTableEntity.getFirstPlayer().getMana());
            infoForPlayer.getEnemyInfo().setHp(gameTableEntity.getSecondPlayer().getHp());
            infoForPlayer.getEnemyInfo().setMana(gameTableEntity.getSecondPlayer().getMana());



        }
        else {
            List<CardStats> cardStatsList =
                    gameTableEntity.getSecondPlayer().getCardsOnHand()
                            .stream()
                            .map(
                                    c -> new CardStats(c)
                            )
                            .collect(
                                    Collectors.toList()
                            );

            infoForPlayer.getYourInfo().setHand(
                    cardStatsList
            );

            infoForPlayer.getYourInfo().setTable(
                    gameTableEntity.getSecondPlayer().getCardsOnTable()
            );

            infoForPlayer.getEnemyInfo().setTable(
                    gameTableEntity.getFirstPlayer().getCardsOnTable()
            );

            infoForPlayer.getEnemyInfo().setHand(
                    gameTableEntity.getFirstPlayer().getCardsOnHand().size()
            );

            infoForPlayer.setIsYourStep(!gameTableEntity.getIsFirstPlayerStep());

            infoForPlayer.getYourInfo().setNickname(gameTableEntity.getLobby().getSecondPlayerId());
            infoForPlayer.getEnemyInfo().setNickname(gameTableEntity.getLobby().getFirstPlayerId());


            infoForPlayer.getYourInfo().setHp(gameTableEntity.getSecondPlayer().getHp());
            infoForPlayer.getYourInfo().setMana(gameTableEntity.getSecondPlayer().getMana());
            infoForPlayer.getEnemyInfo().setHp(gameTableEntity.getFirstPlayer().getHp());
            infoForPlayer.getEnemyInfo().setMana(gameTableEntity.getFirstPlayer().getMana());


        }


        infoForPlayer.setHover(
                gameTableEntity.getHover()
        );

        return infoForPlayer;
    }
}
