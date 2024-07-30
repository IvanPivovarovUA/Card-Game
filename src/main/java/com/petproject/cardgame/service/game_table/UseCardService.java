package com.petproject.cardgame.service.game_table;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UseCardService {

    @Autowired
    GameTableRepository gameTableRepository;

    public void addCardInHand() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        addCardInHand(gameTableEntity.getIsFirstPlayerStep());
    }

    public void addCardInHand(boolean isFirstPlayerStep) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        if (isFirstPlayerStep) {
            gameTableEntity.getFirstPlayer().getCardsOnHand().add(Card.randomCard());
        }
        else {
            gameTableEntity.getSecondPlayer().getCardsOnHand().add(Card.randomCard());
        }

        gameTableRepository.save(gameTableEntity);
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

    public void putCardOnTable(int index) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        if (gameTableEntity.getIsFirstPlayerStep()) {
            if (gameTableEntity.getFirstPlayer().getCardsOnHand().get(index).getMana() <= gameTableEntity.getFirstPlayer().getMana()) {
                gameTableEntity.getFirstPlayer().getCardsOnTable().add(
                        createCardOnTable(
                                gameTableEntity.getFirstPlayer().getCardsOnHand().get(index)
                        )
                );

                gameTableEntity.getFirstPlayer().plusMana(
                        -gameTableEntity.getFirstPlayer().getCardsOnHand().get(index).getMana()
                );

                gameTableEntity.getFirstPlayer().getCardsOnHand().remove(index);
            }


        }
        else {
            if (gameTableEntity.getSecondPlayer().getCardsOnHand().get(index).getMana() <= gameTableEntity.getSecondPlayer().getMana()) {
                gameTableEntity.getSecondPlayer().getCardsOnTable().add(
                        createCardOnTable(
                                gameTableEntity.getSecondPlayer().getCardsOnHand().get(index)
                        )
                );

                gameTableEntity.getSecondPlayer().plusMana(
                        -gameTableEntity.getSecondPlayer().getCardsOnHand().get(index).getMana()
                );

                gameTableEntity.getSecondPlayer().getCardsOnHand().remove(index);
            }
        }

        gameTableRepository.save(gameTableEntity);

    }


    public void attackCard(int MainCardId, int WorkCardId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        CardOnTableEntity MainC;
        CardOnTableEntity WorkC;
        List<CardOnTableEntity> MainCList;
        List<CardOnTableEntity> WorkCList;

        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(MainCardId);
            WorkC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(WorkCardId);
            MainCList = gameTableEntity.getFirstPlayer().getCardsOnTable();
            WorkCList = gameTableEntity.getSecondPlayer().getCardsOnTable();
        }
        else {
            MainC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(WorkCardId);
            WorkC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(MainCardId);
            MainCList = gameTableEntity.getSecondPlayer().getCardsOnTable();
            WorkCList = gameTableEntity.getFirstPlayer().getCardsOnTable();
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
                MainCList.remove(MainCardId);
            }
            if (WorkC.getHp() <= 0) {
                WorkCList.remove(WorkCardId);
            }
        }

        gameTableRepository.save(gameTableEntity);
    }

    public void attackPlayer(int MainCardId) {
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
                System.out.println("end!!!!!");
            }
        }

        gameTableRepository.save(gameTableEntity);
    }

}
