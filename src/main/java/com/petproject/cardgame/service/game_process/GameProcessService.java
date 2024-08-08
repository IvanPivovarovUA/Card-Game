package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameProcessService {

    @Autowired
    GameTableRepository gameTableRepository;

    @Autowired
    UseCardService useCardService;

    @Autowired
    OneClickSpellService oneClickSpellService;

    @Autowired
    TwoClickSpellService twoClickSpellService;

    public String getFirstPlayerId() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        return gameTableEntity.getFirstPlayer().getId();
    }

    public String getSecondPlayerId() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();
        return gameTableEntity.getSecondPlayer().getId();
    }

    public boolean isUserHaveExec(String UserId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        if (
                gameTableEntity.getFirstPlayer().getId().equals(UserId)
                && gameTableEntity.getIsFirstPlayerStep()
        ) {
            return true;
        }
        else if (
                gameTableEntity.getSecondPlayer().getId().equals(UserId)
                && !gameTableEntity.getIsFirstPlayerStep()
        ) {
            return true;
        }
        else {
            return false;
        }
    }

    public void nextPlayerStep() {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        if (gameTableEntity.getIsFirstPlayerStep()) {
            gameTableEntity.setIsFirstPlayerStep(false);
            gameTableEntity.getSecondPlayer().setMana(10);
            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getSecondPlayer().getCardsOnTable()) {
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(true);
                }
            }
            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getFirstPlayer().getCardsOnTable()) {
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(false);
                }
            }
        }
        else {
            gameTableEntity.setIsFirstPlayerStep(true);
            gameTableEntity.getFirstPlayer().setMana(10);
            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getFirstPlayer().getCardsOnTable()) {
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(true);
                }
            }
            for (CardOnTableEntity cardOnTableEntity : gameTableEntity.getSecondPlayer().getCardsOnTable()) {
                if (!cardOnTableEntity.getType().name().equals("W")) {
                    cardOnTableEntity.setCanAttack(false);
                }
            }

        }
        gameTableRepository.save(gameTableEntity);
    }



    public boolean useCard() {
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
                gameTableEntity.getHover().getHand() == -1
                        && gameTableEntity.getHover().getTable() != -1
        ) {
            if (gameTableEntity.getHover().getEnemy() != -1) {
                useCardService.attackCard();
                return true;
            }
            if (gameTableEntity.getHover().getPlayer()) {
                useCardService.attackPlayer();
                return true;
            }
        }

        if (
                gameTableEntity.getHover().getHand() != -1
                        && mainPlayer.getCardsOnHand().get(
                        gameTableEntity.getHover().getHand()
                ).getMana() <= mainPlayer.getMana()

        ) {
            switch (
                    mainPlayer.getCardsOnHand().get(
                            gameTableEntity.getHover().getHand()
                    ).name()
            ) {
                case "A":
                    oneClickSpellService.arrowSpell();
                    useCardService.removeHoverCardFromHand();
                    return true;
                case "F":
                    oneClickSpellService.fireSpell();
                    useCardService.removeHoverCardFromHand();
                    return true;
                case "M":
                    oneClickSpellService.moneySpell();
                    useCardService.removeHoverCardFromHand();
                    return true;
                case "I":
                    useCardService.addCardInHand();
                    useCardService.addCardInHand();
                    useCardService.addCardInHand();
                    useCardService.removeHoverCardFromHand();
                    return true;
                //////////////////////////////
                case "P":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.pivoSpell();
                        useCardService.removeHoverCardFromHand();
                        return true;
                    }
                    break;
                case "S":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.swordSpell();
                        useCardService.removeHoverCardFromHand();
                        return true;
                    }
                    break;
                case "H":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.crossSpell();
                        useCardService.removeHoverCardFromHand();
                        return true;
                    }
                    break;
                case "T":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.turretSpell();
                        useCardService.removeHoverCardFromHand();
                        return true;
                    }
                    break;
                case "z":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.potionSpell();
                        useCardService.removeHoverCardFromHand();
                        return true;
                    }
                    break;
                ////////////////////////////
                default:
                    useCardService.putCardOnTable();
                    useCardService.removeHoverCardFromHand();
                    return true;
            }
        }

        return false;
    }









}
