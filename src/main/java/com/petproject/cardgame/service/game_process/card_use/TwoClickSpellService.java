package com.petproject.cardgame.service.game_process.card_use;

import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.document.PlayerDocument;
import com.petproject.cardgame.data.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwoClickSpellService {

    @Autowired
    GameTableRepository gameTableRepository;

    @Autowired
    CardUseService cardUseService;

    public boolean canIUseSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        int index = gameTableDocument.getHover().getHand();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        if (
            mainPlayer.getCardsOnHand().get(index).name().equals("P")
            || mainPlayer.getCardsOnHand().get(index).name().equals("S")
            || mainPlayer.getCardsOnHand().get(index).name().equals("H")
        ) {
            if (gameTableDocument.getHover().getTable() != -1) {
                return true;
            }
        }
        if (
                mainPlayer.getCardsOnHand().get(index).name().equals("T")
                || mainPlayer.getCardsOnHand().get(index).name().equals("z")
        ) {
            if (gameTableDocument.getHover().getEnemy() != -1) {
                return true;
            }
        }

        if (
                mainPlayer.getCardsOnHand().get(index).name().equals("T")
        ) {
            if (gameTableDocument.getHover().getPlayer()) {
                return true;
            }
        }

        return false;
    }


    public void pivoSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        int index = gameTableDocument.getHover().getTable();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        mainPlayer.getCardsOnTable().get(index).setCanAttack(true);

        mainPlayer.getDropedSpells().add(Card.P);

        gameTableRepository.save(gameTableDocument);
        cardUseService.removeHoverCardFromHand();

    }

    public void swordSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        int index = gameTableDocument.getHover().getTable();
        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        mainPlayer.getCardsOnTable().get(index).plusPower(Card.S.getPower());

        mainPlayer.getDropedSpells().add(Card.S);

        gameTableRepository.save(gameTableDocument);
        cardUseService.removeHoverCardFromHand();
    }

    public void crossSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        int index = gameTableDocument.getHover().getTable();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();

        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        mainPlayer.getCardsOnTable().get(index).plusHp(Card.H.getPower());

        mainPlayer.getDropedSpells().add(Card.H);

        gameTableRepository.save(gameTableDocument);
        cardUseService.removeHoverCardFromHand();
    }

    public void turretSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        int tableIndex = gameTableDocument.getHover().getEnemy();

        PlayerDocument mainPlayer;
        PlayerDocument workPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
            workPlayer = gameTableDocument.getSecondPlayer();

        }
        else {
            workPlayer = gameTableDocument.getFirstPlayer();
            mainPlayer = gameTableDocument.getSecondPlayer();
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

        gameTableRepository.save(gameTableDocument);
        cardUseService.removeHoverCardFromHand();
    }

    public void potionSpell() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        int index = gameTableDocument.getHover().getEnemy();

        PlayerDocument mainPlayer;
        PlayerDocument workPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
            workPlayer = gameTableDocument.getSecondPlayer();

        }
        else {
            workPlayer = gameTableDocument.getFirstPlayer();
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        if (workPlayer.getCardsOnTable().get(index).getPower() != 0) {
            workPlayer.getCardsOnTable().get(index).setPower(1);
        }

        mainPlayer.getDropedSpells().add(Card.z);

        gameTableRepository.save(gameTableDocument);
        cardUseService.removeHoverCardFromHand();
    }


}
