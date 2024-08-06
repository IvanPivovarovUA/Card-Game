package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwoClickSpellService {

    @Autowired
    GameTableRepository gameTableRepository;


    public boolean canIUseSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int index = gameTableEntity.getHover().getHand();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        if (
            mainPlayer.getCardsOnHand().get(index).name().equals("P")
            || mainPlayer.getCardsOnHand().get(index).name().equals("S")
            || mainPlayer.getCardsOnHand().get(index).name().equals("H")
        ) {
            if (gameTableEntity.getHover().getTable() != -1) {
                return true;
            }
        }
        if (
                mainPlayer.getCardsOnHand().get(index).name().equals("T")
                || mainPlayer.getCardsOnHand().get(index).name().equals("z")
        ) {
            if (gameTableEntity.getHover().getEnemy() != -1) {
                return true;
            }
        }

        return false;
    }


    public void pivoSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int index = gameTableEntity.getHover().getTable();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        mainPlayer.getCardsOnTable().get(index).setCanAttack(true);

        gameTableRepository.save(gameTableEntity);
    }

    public void swordSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int index = gameTableEntity.getHover().getTable();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        mainPlayer.getCardsOnTable().get(index).plusPower(Card.S.getPower());

        gameTableRepository.save(gameTableEntity);
    }

    public void crossSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int index = gameTableEntity.getHover().getTable();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        mainPlayer.getCardsOnTable().get(index).plusHp(Card.H.getPower());

        gameTableRepository.save(gameTableEntity);
    }

    public void turretSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int index = gameTableEntity.getHover().getEnemy();

        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            workPlayer = gameTableEntity.getSecondPlayer();

        }
        else {
            workPlayer = gameTableEntity.getFirstPlayer();
        }

        if (index != -1) {
            workPlayer.getCardsOnTable().get(index).plusHp(-Card.T.getPower());
            if (workPlayer.getCardsOnTable().get(index).getHp() <= 0) {
                workPlayer.getCardsOnTable().remove(index);
            }
        }
        else {
            workPlayer.plusHp(-Card.T.getPower());
            if (workPlayer.getHp() <= 0) {
                System.out.println("end in TwoClickSpellService!!!!!!!!!");
            }
        }

        gameTableRepository.save(gameTableEntity);
    }

    public void potionSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int index = gameTableEntity.getHover().getEnemy();

        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            workPlayer = gameTableEntity.getSecondPlayer();

        }
        else {
            workPlayer = gameTableEntity.getFirstPlayer();
        }

        if (workPlayer.getCardsOnTable().get(index).getPower() != 0) {
            workPlayer.getCardsOnTable().get(index).setPower(1);
        }

        gameTableRepository.save(gameTableEntity);
    }


}
