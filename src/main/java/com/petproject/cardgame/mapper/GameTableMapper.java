package com.petproject.cardgame.mapper;

import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.document.PlayerDocument;
import com.petproject.cardgame.data.dto.CardDto;
import com.petproject.cardgame.data.dto.EnemyInfoDto;
import com.petproject.cardgame.data.dto.InfoForPlayerDto;
import com.petproject.cardgame.data.dto.YourInfoDto;


import java.util.List;
import java.util.stream.Collectors;

public class GameTableMapper {

    static public InfoForPlayerDto GameTableToInfoForPlayer(GameTableDocument gameTableDocument, boolean isItInfoForFirstPlayer) {
        InfoForPlayerDto infoForPlayerDto = new InfoForPlayerDto();
        infoForPlayerDto.setYourInfo(new YourInfoDto());
        infoForPlayerDto.setEnemyInfo(new EnemyInfoDto());

        PlayerDocument mainPlayer;
        PlayerDocument workPlayer;
        if (isItInfoForFirstPlayer) {
            mainPlayer = gameTableDocument.getFirstPlayer();
            workPlayer = gameTableDocument.getSecondPlayer();
            infoForPlayerDto.setIsYourStep(gameTableDocument.getIsFirstPlayerStep());

        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
            workPlayer = gameTableDocument.getFirstPlayer();
            infoForPlayerDto.setIsYourStep(!gameTableDocument.getIsFirstPlayerStep());
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

        infoForPlayerDto.getYourInfo().setHand(
                cardDtoList
        );

        infoForPlayerDto.getYourInfo().setTable(
                mainPlayer.getCardsOnTable()
        );

        infoForPlayerDto.getEnemyInfo().setTable(
                workPlayer.getCardsOnTable()
        );

        infoForPlayerDto.getEnemyInfo().setHand(
                workPlayer.getCardsOnHand().size()
        );


        infoForPlayerDto.getYourInfo().setNickname(mainPlayer.getUserInfo().getNickname());
        infoForPlayerDto.getEnemyInfo().setNickname(workPlayer.getUserInfo().getNickname());

        infoForPlayerDto.getYourInfo().setHp(mainPlayer.getHp());
        infoForPlayerDto.getYourInfo().setMana(mainPlayer.getMana());
        infoForPlayerDto.getEnemyInfo().setHp(workPlayer.getHp());
        infoForPlayerDto.getEnemyInfo().setMana(workPlayer.getMana());

        infoForPlayerDto.setHover(
                gameTableDocument.getHover()
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
        infoForPlayerDto.getYourInfo().setDropedCards(cardDtoList);


        cardDtoList =
                workPlayer.getDropedSpells()
                        .stream()
                        .map(
                                c -> new CardDto(c)
                        )
                        .collect(
                                Collectors.toList()
                        );
        infoForPlayerDto.getEnemyInfo().setDropedCards(cardDtoList);

        return infoForPlayerDto;
    }
}
