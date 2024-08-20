package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.document.HoverDocument;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.game_process.card_use.CardUseAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CardHoverService {

    @Autowired
    GameTableRepository gameTableRepository;

    @Autowired
    CardUseAccessService cardUseAccessService;

    public HoverDocument getHover() {
        return gameTableRepository.findById("1").get().getHover();
    }


    public void reset() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        gameTableDocument.setHover(new HoverDocument());
        gameTableRepository.save(gameTableDocument);
    }




    public void hover(Integer index, Character place) {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        place = Character.toUpperCase(place);



        if (place.equals('H')) {
            gameTableDocument.setHover(new HoverDocument());
            gameTableDocument.getHover().setHand(index);
        }

        if (place.equals('T')) {
            String[] cardList1 = {"P", "H", "S"};
            String[] cardList2 = {"z", "T"};


            if (
                    gameTableDocument.getHover().getHand() != -1
                    && isItTwoClickSpell(cardList1)
            ) {
                gameTableDocument.getHover().setTable(index);
            }
            else if(
                    gameTableDocument.getHover().getHand() != -1
                    && !isItTwoClickSpell(cardList2)
//                    && isCardCanAttack(index)
            ) {
                gameTableDocument.setHover(new HoverDocument());
                gameTableDocument.getHover().setTable(index);
            }
            else if(
                    gameTableDocument.getHover().getHand() == -1
//                    && isCardCanAttack(index)
            ) {
                gameTableDocument.setHover(new HoverDocument());
                gameTableDocument.getHover().setTable(index);
            }


        }

        if (place.equals('E')) {
            String[] cardList = {"z", "T", "E"};

            if (
                    gameTableDocument.getHover().getHand() != -1
                    && isItTwoClickSpell(cardList)
            ) {
                gameTableDocument.getHover().setEnemy(index);
                gameTableDocument.getHover().setPlayer(false);
            }
            else if (
                    gameTableDocument.getHover().getHand() == -1
                    && gameTableDocument.getHover().getTable() != -1
//                    && !cardUseAccessService.isThereAnyArmorCard(index)
            ) {
                gameTableDocument.getHover().setEnemy(index);
                gameTableDocument.getHover().setPlayer(false);
            }

        }

        if (place.equals('P')) {
            if (
                    gameTableDocument.getHover().getHand() == -1
                    && gameTableDocument.getHover().getTable() != -1
//                    && !cardUseAccessService.isThereAnyArmorCard()
            ) {
                gameTableDocument.getHover().setEnemy(-1);
                gameTableDocument.getHover().setPlayer(true);
            }

            String[] cardList = {"T"};
            if (
                    gameTableDocument.getHover().getHand() != -1
                    && isItTwoClickSpell(cardList)
            ) {
                gameTableDocument.getHover().setEnemy(-1);
                gameTableDocument.getHover().setPlayer(true);
            }

        }

        gameTableRepository.save(gameTableDocument);
    }

//    public boolean isThereAnyArmorCard(int index) {
//        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
//
//        PlayerEntity workPlayer;
//        if (gameTableEntity.getIsFirstPlayerStep()) {
//            workPlayer = gameTableEntity.getSecondPlayer();
//        }
//        else {
//
//            workPlayer = gameTableEntity.getFirstPlayer();
//        }
//
//        if (
//                workPlayer.getCardsOnTable().get(index).getType().name().equals("W")
//                || workPlayer.getCardsOnTable().get(index).getType().name().equals("Z")
//        ) {
//            return false;
//        }
//
//        for (CardOnTableEntity cardOnTableEntity: workPlayer.getCardsOnTable()) {
//            if (
//                    cardOnTableEntity.getType().name().equals("W")
//                    || cardOnTableEntity.getType().name().equals("Z")
//            ) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean isThereAnyArmorCard() {
//        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
//
//        PlayerEntity workPlayer;
//        if (gameTableEntity.getIsFirstPlayerStep()) {
//            workPlayer = gameTableEntity.getSecondPlayer();
//        }
//        else {
//
//            workPlayer = gameTableEntity.getFirstPlayer();
//        }
//
//
//        for (CardOnTableEntity cardOnTableEntity: workPlayer.getCardsOnTable()) {
//            if (
//                    cardOnTableEntity.getType().name().equals("W")
//                            || cardOnTableEntity.getType().name().equals("Z")
//            ) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isItTwoClickSpell(String[] cardList) {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        String cardString;

        if (gameTableDocument.getIsFirstPlayerStep()) {
            cardString = gameTableDocument.getFirstPlayer().getCardsOnHand().get(
                    gameTableDocument.getHover().getHand()
            ).name();
        }
        else {
            cardString = gameTableDocument.getSecondPlayer().getCardsOnHand().get(
                    gameTableDocument.getHover().getHand()
            ).name();
        }

        for (String c : cardList) {
            if (cardString.equals(c) ){
                return true;
            }
        }
        return false;
    }

//    public boolean isCardCanAttack(int index) {
//        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
//
//        PlayerEntity mainPlayer;
//        if (gameTableEntity.getIsFirstPlayerStep()) {
//            mainPlayer = gameTableEntity.getFirstPlayer();
//        }
//        else {
//            mainPlayer = gameTableEntity.getSecondPlayer();
//        }
//
//        return mainPlayer.getCardsOnTable()
//                .get(index)
//                .getCanAttack();
//
//    }


}
