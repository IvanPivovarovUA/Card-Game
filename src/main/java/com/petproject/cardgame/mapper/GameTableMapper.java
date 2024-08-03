package com.petproject.cardgame.mapper;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class GameTableMapper {

    static public InfoForPlayerDto GameTableToInfoForPlayer(GameTableEntity gameTableEntity, boolean isItInfoForFirstPlayer) {
        InfoForPlayerDto infoForPlayerDto = new InfoForPlayerDto();
        infoForPlayerDto.setYourInfo(new YourInfo());
        infoForPlayerDto.setEnemyInfo(new EnemyInfo());

        PlayerEntity MainP;
        PlayerEntity WorkP;
        if (isItInfoForFirstPlayer) {
            MainP = gameTableEntity.getFirstPlayer();
            WorkP = gameTableEntity.getSecondPlayer();
            infoForPlayerDto.setIsYourStep(gameTableEntity.getIsFirstPlayerStep());

        }
        else {
            MainP = gameTableEntity.getSecondPlayer();
            WorkP = gameTableEntity.getFirstPlayer();
            infoForPlayerDto.setIsYourStep(!gameTableEntity.getIsFirstPlayerStep());
        }

        List<CardDto> cardDtoList =
            MainP.getCardsOnHand()
                .stream()
                .map(
                        c -> new CardDto(c)
                )
                .collect(
                        Collectors.toList()
                );

        infoForPlayerDto.getYourInfo().setHand(
                cardDtoList
        );

        infoForPlayerDto.getYourInfo().setTable(
                MainP.getCardsOnTable()
        );

        infoForPlayerDto.getEnemyInfo().setTable(
                WorkP.getCardsOnTable()
        );

        infoForPlayerDto.getEnemyInfo().setHand(
                WorkP.getCardsOnHand().size()
        );


        infoForPlayerDto.getYourInfo().setNickname(MainP.getId());
        infoForPlayerDto.getEnemyInfo().setNickname(WorkP.getId());

        infoForPlayerDto.getYourInfo().setHp(MainP.getHp());
        infoForPlayerDto.getYourInfo().setMana(MainP.getMana());
        infoForPlayerDto.getEnemyInfo().setHp(WorkP.getHp());
        infoForPlayerDto.getEnemyInfo().setMana(WorkP.getMana());

        infoForPlayerDto.setHover(
                gameTableEntity.getHover()
        );

        return infoForPlayerDto;
    }
}
