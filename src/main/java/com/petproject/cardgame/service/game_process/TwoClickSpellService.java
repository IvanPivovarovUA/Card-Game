package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.game_process.card_use.CardUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwoClickSpellService {

    @Autowired
    GameTableRepository gameTableRepository;

    @Autowired
    CardUseService cardUseService;

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

        if (
                mainPlayer.getCardsOnHand().get(index).name().equals("T")
        ) {
            if (gameTableEntity.getHover().getPlayer()) {
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

        mainPlayer.getDropedSpells().add(Card.P);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();

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

        mainPlayer.getDropedSpells().add(Card.S);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();
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

        mainPlayer.getDropedSpells().add(Card.H);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();
    }

    public void turretSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int tableIndex = gameTableEntity.getHover().getEnemy();

        PlayerEntity mainPlayer;
        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
            workPlayer = gameTableEntity.getSecondPlayer();

        }
        else {
            workPlayer = gameTableEntity.getFirstPlayer();
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        if (tableIndex != -1) {
            workPlayer.getCardsOnTable().get(tableIndex).plusHp(-Card.T.getPower());
            if (workPlayer.getCardsOnTable().get(tableIndex).getHp() <= 0) {
                workPlayer.getCardsOnTable().remove(tableIndex);
            }
        }
        else {
            workPlayer.plusHp(-Card.T.getPower());
            if (workPlayer.getHp() <= 0) {
                System.out.println("end in TwoClickSpellService!!!!!!!!!");
            }
        }

        mainPlayer.getDropedSpells().add(Card.T);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();
    }

    public void potionSpell() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        int index = gameTableEntity.getHover().getEnemy();

        PlayerEntity mainPlayer;
        PlayerEntity workPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
            workPlayer = gameTableEntity.getSecondPlayer();

        }
        else {
            workPlayer = gameTableEntity.getFirstPlayer();
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        if (workPlayer.getCardsOnTable().get(index).getPower() != 0) {
            workPlayer.getCardsOnTable().get(index).setPower(1);
        }

        mainPlayer.getDropedSpells().add(Card.P);

        gameTableRepository.save(gameTableEntity);
        cardUseService.removeHoverCardFromHand();
    }


}
