package com.petproject.cardgame.mapper;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class GameTableMapper {

    static public InfoForPlayerDto GameTableToInfoForPlayer(GameTableEntity gameTableEntity, boolean isItInfoForFirstPlayer) {
        InfoForPlayerDto infoForPlayerDto = new InfoForPlayerDto();
        infoForPlayerDto.setYourInfoDto(new YourInfoDto());
        infoForPlayerDto.setEnemyInfoDto(new EnemyInfoDto());

        PlayerEntity mainPlayer;
        PlayerEntity workPlayer;
        if (isItInfoForFirstPlayer) {
            mainPlayer = gameTableEntity.getFirstPlayer();
            workPlayer = gameTableEntity.getSecondPlayer();
            infoForPlayerDto.setIsYourStep(gameTableEntity.getIsFirstPlayerStep());

        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
            workPlayer = gameTableEntity.getFirstPlayer();
            infoForPlayerDto.setIsYourStep(!gameTableEntity.getIsFirstPlayerStep());
        }

        List<CardDto> cardDtoList =
            mainPlayer.getCardsOnHand()
                .stream()
                .map(
                        c -> new CardDto(c)
                )
                .collect(
                        Collectors.toList()
                );

        infoForPlayerDto.getYourInfoDto().setHand(
                cardDtoList
        );

        infoForPlayerDto.getYourInfoDto().setTable(
                mainPlayer.getCardsOnTable()
        );

        infoForPlayerDto.getEnemyInfoDto().setTable(
                workPlayer.getCardsOnTable()
        );

        infoForPlayerDto.getEnemyInfoDto().setHand(
                workPlayer.getCardsOnHand().size()
        );


        infoForPlayerDto.getYourInfoDto().setNickname(mainPlayer.getId());
        infoForPlayerDto.getEnemyInfoDto().setNickname(workPlayer.getId());

        infoForPlayerDto.getYourInfoDto().setHp(mainPlayer.getHp());
        infoForPlayerDto.getYourInfoDto().setMana(mainPlayer.getMana());
        infoForPlayerDto.getEnemyInfoDto().setHp(workPlayer.getHp());
        infoForPlayerDto.getEnemyInfoDto().setMana(workPlayer.getMana());

        infoForPlayerDto.setHover(
                gameTableEntity.getHover()
        );


        cardDtoList =
                mainPlayer.getDropedSpells()
                        .stream()
                        .map(
                                c -> new CardDto(c)
                        )
                        .collect(
                                Collectors.toList()
                        );
        infoForPlayerDto.getYourInfoDto().setDropedCards(cardDtoList);


        cardDtoList =
                workPlayer.getDropedSpells()
                        .stream()
                        .map(
                                c -> new CardDto(c)
                        )
                        .collect(
                                Collectors.toList()
                        );
        infoForPlayerDto.getEnemyInfoDto().setDropedCards(cardDtoList);

        return infoForPlayerDto;
    }
}
