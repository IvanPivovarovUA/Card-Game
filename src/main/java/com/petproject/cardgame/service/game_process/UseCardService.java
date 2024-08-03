package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        PlayerEntity MainP;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            MainP = gameTableEntity.getFirstPlayer();
        }
        else {
            MainP = gameTableEntity.getSecondPlayer();
        }

        if (MainP.getCardsOnHand().get(index).getMana() <= MainP.getMana()) {
            MainP.getCardsOnTable().add(
                    createCardOnTable(
                            MainP.getCardsOnHand().get(index)
                    )
            );

            MainP.plusMana(
                    -MainP.getCardsOnHand().get(index).getMana()
            );

            MainP.getCardsOnHand().remove(index);
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
            MainC = gameTableEntity.getSecondPlayer().getCardsOnTable().get(MainCardId);
            WorkC = gameTableEntity.getFirstPlayer().getCardsOnTable().get(WorkCardId);
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
