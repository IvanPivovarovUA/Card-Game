package com.petproject.cardgame.service.game_process.card_use;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.model.Card;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.petproject.cardgame.model.Card.getArmorCards;

@Service
public class CardUseAccessService {

    @Autowired
    GameTableRepository gameTableRepository;

    public boolean canIAttackCard() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        int tableIndex = gameTableEntity.getHover().getTable();
        int enemyIndex = gameTableEntity.getHover().getEnemy();
        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        if (
                gameTableEntity.getHover().getHand() == -1
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
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        if (
                gameTableEntity.getHover().getHand() == -1
                && gameTableEntity.getHover().getTable() != -1
                && gameTableEntity.getHover().getPlayer()
                && !isThereAnyArmorCard()
        ) {
            return true;
        }
        return false;
    }


    public boolean isThereEnoughMana() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        int index = gameTableEntity.getHover().getHand();

        PlayerEntity mainPlayer;
        if (gameTableEntity.getIsFirstPlayerStep()) {
            mainPlayer = gameTableEntity.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableEntity.getSecondPlayer();
        }

        return index != -1 && mainPlayer.getCardsOnHand().get(index).getMana() <= mainPlayer.getMana();
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

        for (Card card : getArmorCards()) {
            if (
                    workPlayer.getCardsOnTable().get(index).getType().name().equals(card.name())
            ) {
                return false;
            }
        }


        for (CardOnTableEntity cardOnTableEntity: workPlayer.getCardsOnTable()) {
            for (Card card : getArmorCards()) {
                if (
                        cardOnTableEntity.getType().name().equals(card.name())
                ) {
                    return true;
                }
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
            for (Card card : getArmorCards()) {
                if (
                        cardOnTableEntity.getType().name().equals(card.name())
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
