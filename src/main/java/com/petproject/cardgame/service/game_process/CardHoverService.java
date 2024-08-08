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


    public void reset() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        gameTableEntity.setHover(new HoverEntity());
        gameTableRepository.save(gameTableEntity);
    }




    public void hover(Integer index, Character place) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        place = Character.toUpperCase(place);



        if (place.equals('H')) {
            gameTableEntity.setHover(new HoverEntity());
            gameTableEntity.getHover().setHand(index);
        }

        if (place.equals('T')) {
            String[] cardList1 = {"P", "H", "S"};
            String[] cardList2 = {"z", "T"};


            if (
                    gameTableEntity.getHover().getHand() != -1
                    && isItTwoClickSpell(cardList1)
            ) {
                gameTableEntity.getHover().setTable(index);
            }
            else if(
                    gameTableEntity.getHover().getHand() != -1
                    && !isItTwoClickSpell(cardList2)
                    && isCardCanAttack(index)
            ) {
                gameTableEntity.setHover(new HoverEntity());
                gameTableEntity.getHover().setTable(index);
            }
            else if(
                    gameTableEntity.getHover().getHand() == -1
                    && isCardCanAttack(index)
            ) {
                gameTableEntity.setHover(new HoverEntity());
                gameTableEntity.getHover().setTable(index);
            }


        }

        if (place.equals('E')) {
            String[] cardList = {"z", "T", "E"};

            if (
                    gameTableEntity.getHover().getHand() != -1
                    && isItTwoClickSpell(cardList)
            ) {
                gameTableEntity.getHover().setEnemy(index);
            }
            else if (
                    gameTableEntity.getHover().getHand() == -1
                    && gameTableEntity.getHover().getTable() != -1
                    && !isThereAnyArmorCard(index)
            ) {
                gameTableEntity.getHover().setEnemy(index);
            }

        }

        if (place.equals('P')) {
            if (
                    gameTableEntity.getHover().getHand() == -1
                    && gameTableEntity.getHover().getTable() != -1
                    && !isThereAnyArmorCard()
            ) {
                gameTableEntity.getHover().setPlayer(true);
            }

            String[] cardList = {"T"};
            if (
                    gameTableEntity.getHover().getHand() != -1
                    && isItTwoClickSpell(cardList)
            ) {
                gameTableEntity.getHover().setPlayer(true);
            }

        }

        gameTableRepository.save(gameTableEntity);
    }

    public boolean isThereAnyArmorCard(int index) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            workPlayer = gameTableEntity.getSecondPlayer();
        }
        else {

            workPlayer = gameTableEntity.getFirstPlayer();
        }

        if (
                workPlayer.getCardsOnTable().get(index).getType().name().equals("W")
                || workPlayer.getCardsOnTable().get(index).getType().name().equals("Z")
        ) {
            return false;
        }

        for (CardOnTableEntity cardOnTableEntity: workPlayer.getCardsOnTable()) {
            if (
                    cardOnTableEntity.getType().name().equals("W")
                    || cardOnTableEntity.getType().name().equals("Z")
            ) {
                return true;
            }
        }
        return false;
    }

    public boolean isThereAnyArmorCard() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            workPlayer = gameTableEntity.getSecondPlayer();
        }
        else {

            workPlayer = gameTableEntity.getFirstPlayer();
        }


        for (CardOnTableEntity cardOnTableEntity: workPlayer.getCardsOnTable()) {
            if (
                    cardOnTableEntity.getType().name().equals("W")
                            || cardOnTableEntity.getType().name().equals("Z")
            ) {
                return true;
            }
        }
        return false;
    }



    public boolean isItTwoClickSpell(String[] cardList) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        String cardString;

        if (gameTableEntity.getIsFirstPlayerStep()) {
            cardString = gameTableEntity.getFirstPlayer().getCardsOnHand().get(
                    gameTableEntity.getHover().getHand()
            ).name();
        }
        else {
            cardString = gameTableEntity.getSecondPlayer().getCardsOnHand().get(
                    gameTableEntity.getHover().getHand()
            ).name();
        }


        for (String c : cardList) {
            if (cardString.equals(c) ){
                return true;
            }
        }
        return false;
    }

    public boolean isCardCanAttack(int index) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        return mainPlayer.getCardsOnTable()
                .get(index)
                .getCanAttack();

    }


}
