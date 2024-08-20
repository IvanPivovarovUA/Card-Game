package com.petproject.cardgame.service.game_process;

import com.petproject.cardgame.data.document.CardOnTableDocument;
import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.document.PlayerDocument;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.game_process.card_use.CardUseAccessService;
import com.petproject.cardgame.service.game_process.card_use.CardUseService;
import com.petproject.cardgame.service.game_process.card_use.OneClickSpellService;
import com.petproject.cardgame.service.game_process.card_use.TwoClickSpellService;
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
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        return gameTableDocument.getFirstPlayer().getUserInfo().getId();
    }

    public String getSecondPlayerId() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        return gameTableDocument.getSecondPlayer().getUserInfo().getId();
    }

    public boolean isUserHaveExec(String UserId) {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        if (!gameTableDocument.getIsGameContinues())
        {
            return false;
        }

        if (
                gameTableDocument.getFirstPlayer().getUserInfo().getId().equals(UserId)
                && gameTableDocument.getIsFirstPlayerStep()
        ) {
            return true;
        }
        else if (
                gameTableDocument.getSecondPlayer().getUserInfo().getId().equals(UserId)
                && !gameTableDocument.getIsFirstPlayerStep()
        ) {
            return true;
        }
        else {
            return false;
        }
    }

    public void nextPlayerStep() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        if (gameTableDocument.getIsFirstPlayerStep()) {
            gameTableDocument.setIsFirstPlayerStep(false);
            gameTableDocument.getSecondPlayer().setMana(10);
            for (CardOnTableDocument cardOnTableDocument : gameTableDocument.getSecondPlayer().getCardsOnTable()) {
                if (!cardOnTableDocument.getType().name().equals("W")) {
                    cardOnTableDocument.setCanAttack(true);
                }
            }
            for (CardOnTableDocument cardOnTableDocument : gameTableDocument.getFirstPlayer().getCardsOnTable()) {
                if (!cardOnTableDocument.getType().name().equals("W")) {
                    cardOnTableDocument.setCanAttack(false);
                }
            }
            gameTableDocument.getFirstPlayer().getDropedSpells().clear();
        }
        else {
            gameTableDocument.setIsFirstPlayerStep(true);
            gameTableDocument.getFirstPlayer().setMana(10);
            for (CardOnTableDocument cardOnTableDocument : gameTableDocument.getFirstPlayer().getCardsOnTable()) {
                if (!cardOnTableDocument.getType().name().equals("W")) {
                    cardOnTableDocument.setCanAttack(true);
                }
            }
            for (CardOnTableDocument cardOnTableDocument : gameTableDocument.getSecondPlayer().getCardsOnTable()) {
                if (!cardOnTableDocument.getType().name().equals("W")) {
                    cardOnTableDocument.setCanAttack(false);
                }
            }
            gameTableDocument.getSecondPlayer().getDropedSpells().clear();
        }

        gameTableRepository.save(gameTableDocument);
    }



    public boolean useCard() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();
        int index = gameTableDocument.getHover().getHand();
        PlayerDocument mainPlayer;
        if (gameTableDocument.getIsFirstPlayerStep()) {
            mainPlayer = gameTableDocument.getFirstPlayer();
        }
        else {
            mainPlayer = gameTableDocument.getSecondPlayer();
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


    public boolean checkAndDoGameOver() {
        GameTableDocument gameTableDocument = gameTableRepository.findById("1").get();

        if (
                gameTableDocument.getFirstPlayer().getHp() <= 0

        ) {
            gameTableDocument.setWinner(
                    gameTableDocument.getFirstPlayer().getUserInfo().getNickname()
            );
        }

        if (
                gameTableDocument.getSecondPlayer().getHp() <= 0
        ) {
            gameTableDocument.setWinner(
                    gameTableDocument.getSecondPlayer().getUserInfo().getNickname()
            );
        }

        if (
                gameTableDocument.getFirstPlayer().getHp() <= 0
                || gameTableDocument.getSecondPlayer().getHp() <= 0
        ) {
            gameTableDocument.setIsGameContinues(false);
            gameTableRepository.save(gameTableDocument);
            return true;
        }
        return false;
    }
}
