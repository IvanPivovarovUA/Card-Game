package com.petproject.cardgame.service.game_process.card_use;

import com.petproject.cardgame.data.document.CardOnTableDocument;
import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.document.PlayerDocument;
import com.petproject.cardgame.data.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.petproject.cardgame.data.model.Card.getArmorCards;

@Service
public class CardUseAccessService {

    @Autowired
    GameTableRepository gameTableRepository;

    public boolean canIAttackCard() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        int tableIndex = gameTableDocument.getHover().getTable();
        int enemyIndex = gameTableDocument.getHover().getEnemy();
        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        if (
                gameTableDocument.getHover().getHand() == -1
                && tableIndex != -1
                && enemyIndex != -1
                && !isThereAnyArmorCard(enemyIndex)
        ) {

            return mainPlayer
                    .getCardsOnTable()
                    .get(tableIndex)
                    .getCanAttack();
        }
        return false;
    }

    public boolean canIAttackPlayer() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        if (
                gameTableDocument.getHover().getHand() == -1
                && gameTableDocument.getHover().getTable() != -1
                && gameTableDocument.getHover().getPlayer()
                && !isThereAnyArmorCard()
        ) {
            return true;
        }
        return false;
    }


    public boolean isThereEnoughMana() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        int index = gameTableDocument.getHover().getHand();

        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
        }

        return index != -1 && mainPlayer.getCardsOnHand().get(index).getMana() <= mainPlayer.getMana();
    }


    public boolean isThereAnyArmorCard(int index) {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        PlayerDocument workPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            workPlayer = gameTableDocument.getSecondPlayer();
        }
        else {
            workPlayer = gameTableDocument.getFirstPlayer();
        }

        for (Card card : getArmorCards()) {
            if (
                    workPlayer.getCardsOnTable().get(index).getType().name().equals(card.name())
            ) {
                return false;
            }
        }


        for (CardOnTableDocument cardOnTableDocument : workPlayer.getCardsOnTable()) {
            for (Card card : getArmorCards()) {
                if (
                        cardOnTableDocument.getType().name().equals(card.name())
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereAnyArmorCard() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        PlayerDocument workPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            workPlayer = gameTableDocument.getSecondPlayer();
        }
        else {
            workPlayer = gameTableDocument.getFirstPlayer();
        }

        for (CardOnTableDocument cardOnTableDocument : workPlayer.getCardsOnTable()) {
            for (Card card : getArmorCards()) {
                if (
                        cardOnTableDocument.getType().name().equals(card.name())
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
