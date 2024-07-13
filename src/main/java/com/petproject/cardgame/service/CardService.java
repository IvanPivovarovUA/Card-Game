package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CardService {

    @Autowired
    GameTableRepository gameTableRepository;

    private void addCardInHand() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        if (gameTableEntity.getIsFirstPlayerStep()) {
            gameTableEntity.getFirstPlayer().getCardsOnHand().add(Card.randomCard());
        }
        else {
            gameTableEntity.getSecondPlayer().getCardsOnHand().add(Card.randomCard());
        }
    }

    private CardOnTableEntity createCardOnTable(Card card) {
        CardOnTableEntity cardOnTableEntity = new CardOnTableEntity();

        cardOnTableEntity.setType(card);
        cardOnTableEntity.setHp(card.getHp());
        cardOnTableEntity.setPower(card.getPower());

        if (
               card.equals(Card.B)
            || card.equals(Card.R)
            || card.equals(Card.r)
        ) {
            cardOnTableEntity.setCanAttack(true);
        }
        else {
            cardOnTableEntity.setCanAttack(false);
        }

        return cardOnTableEntity;
    }

    private void useCard(int CardId) {
        useCard(CardId,Optional.empty());
    }

    private void useCard(int CardId, int WorkCardId) {
        useCard(CardId,Optional.of(WorkCardId));
    }

    private void useCard(int MainCardId, Optional<Integer> WorkCardId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        PlayerEntity MainP;
        PlayerEntity WorkP;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainP = gameTableEntity.getFirstPlayer();
            WorkP = gameTableEntity.getSecondPlayer();
        }
        else {
            MainP = gameTableEntity.getSecondPlayer();
            WorkP = gameTableEntity.getFirstPlayer();
        }

        Card card = MainP.getCardsOnHand().get(MainCardId);

        if (MainP.getMana() >= card.getMana()) {
            MainP.plusMana(-card.getMana());
            switch (card) {
                case Card.H:
                    MainP
                            .getCardsOnTable()
                            .get(WorkCardId.get())
                            .plusHp(Card.H.getHp());
                    break;
                case Card.P:
                    MainP
                            .getCardsOnTable()
                            .get(WorkCardId.get())
                            .plusPower(Card.P.getPower());
                    break;
                case Card.T:
                    WorkP
                            .getCardsOnTable()
                            .get(WorkCardId.get())
                            .plusPower(-Card.T.getPower());
                    break;
                case Card.F:
                    for (CardOnTableEntity cardOnTableEntity : MainP.getCardsOnTable()) {
                        cardOnTableEntity.plusHp(-card.getPower());
                    }
                    for (CardOnTableEntity cardOnTableEntity : WorkP.getCardsOnTable()) {
                        cardOnTableEntity.plusHp(-card.getPower());
                    }
                    break;
                case Card.A:
                    for (CardOnTableEntity cardOnTableEntity : WorkP.getCardsOnTable()) {
                        cardOnTableEntity.plusHp(-card.getPower());
                    }
                    break;
                default:
                    MainP.getCardsOnTable().add(createCardOnTable(card));
                    break;
            }
            //gameTableRepository.save(gameTableEntity);
        }

    }

    private void unitAtack(int MainCardId, int WorkCardId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        CardOnTableEntity MainC;
        CardOnTableEntity WorkC;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(MainCardId);
            WorkC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(MainCardId);
        }
        else {
            MainC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(WorkCardId);
            WorkC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(MainCardId);
        }

        if (MainC.getCanAttack()) {
            MainC.plusHp(
                    -WorkC.getPower()
            );
            WorkC.plusHp(
                    -MainC.getPower()
            );

            MainC.setCanAttack(false);

            if (MainC.getHp() <= 0) {
                gameTableEntity.getFirstPlayer().getCardsOnTable().remove(MainCardId);
            }
            if (WorkC.getHp() <= 0) {
                gameTableEntity.getFirstPlayer().getCardsOnTable().remove(WorkCardId);
            }
        }
    }

    private void unitAtack(int MainCardId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        CardOnTableEntity MainC;
        PlayerEntity WorkP;

        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(MainCardId);
            WorkP = gameTableEntity.getSecondPlayer();
        }
        else {
            MainC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(MainCardId);
            WorkP = gameTableEntity.getFirstPlayer();
        }

        if (MainC.getCanAttack()) {
            WorkP.plusHp(-MainC.getPower());

            MainC.setCanAttack(false);

            if (WorkP.getHp() <= 0) {
                //end
            }
        }
    }

}
