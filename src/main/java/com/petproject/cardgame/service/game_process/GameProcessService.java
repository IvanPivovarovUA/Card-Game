package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.game_process.card_use.CardUseAccessService;
import com.petproject.cardgame.service.game_process.card_use.CardUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameProcessService {

    @Autowired
    GameTableRepository gameTableRepository;

    @Autowired
    CardUseService cardUseService;

    @Autowired
    CardUseAccessService cardUseAccessService;

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
            gameTableEntity.getFirstPlayer().getDropedSpells().clear();
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
            gameTableEntity.getSecondPlayer().getDropedSpells().clear();
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


        if (cardUseAccessService.canIAttackCard()) {
            cardUseService.attackCard();
            return true;
        }
        if (cardUseAccessService.canIAttackPlayer()) {
            cardUseService.attackPlayer();
            return true;
        }


        if (
                cardUseAccessService.isThereEnoughMana()
        ) {
            switch (
                    cardUseService.getCardInHand().get().name()
            ) {
                case "A":
                    oneClickSpellService.arrowSpell();
                    return true;
                case "F":
                    oneClickSpellService.fireSpell();
                    return true;
                case "M":
                    oneClickSpellService.moneySpell();
                    return true;
                case "I":
                    oneClickSpellService.ishakSpell();
                    return true;
                case "m":
                    oneClickSpellService.manOnWar();
                    return true;
                //////////////////////////////
                case "P":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.pivoSpell();
                        return true;
                    }
                    break;
                case "S":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.swordSpell();
                        return true;
                    }
                    break;
                case "H":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.crossSpell();
                        return true;
                    }
                    break;
                case "T":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.turretSpell();
                        return true;
                    }
                    break;
                case "z":
                    if (twoClickSpellService.canIUseSpell()) {
                        twoClickSpellService.potionSpell();
                        return true;
                    }
                    break;
                ////////////////////////////
                default:
                    oneClickSpellService.defaultUnitCard();
                    return true;
            }
        }

        return false;
    }









}
