package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.LobbyEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.model.Card;

import java.util.ArrayList;
import java.util.Random;
import java.util.Optional;


@Service
public class LobbyService {

    @Autowired
    private GameTableRepository gameTableRepository;

    @Autowired
    private CardService cardService;

    //override
    public GameTableEntity getGameTable() {

        Optional<GameTableEntity> gameTableEntityOptional = gameTableRepository.findById("1");

        if (gameTableEntityOptional.isEmpty()) {
            GameTableEntity gameTableEntity = new GameTableEntity();
            gameTableEntity.setId("1");
            gameTableEntity.setIsGameContinues(false);

            LobbyEntity lobbyEntity = new LobbyEntity();
            lobbyEntity.setWantToPlayUsers(new ArrayList<>());
            gameTableEntity.setLobbyEntity(lobbyEntity);

            gameTableRepository.save(gameTableEntity);

            return gameTableEntity;
        }
        else {

            return gameTableEntityOptional.get();
        }
    }

    public boolean isUserInGame(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();

        if (
            null != gameTableEntity.getLobbyEntity().getFirstPlayerId()
            && gameTableEntity.getLobbyEntity().getFirstPlayerId().equals(UserId)
        ) {
            return true;
        }

        if (
            null != gameTableEntity.getLobbyEntity().getSecondPlayerId()
            && gameTableEntity.getLobbyEntity().getSecondPlayerId().equals(UserId)
        ) {
            return true;
        }

        return false;
    }

    public boolean isUserInLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();
        if (
                gameTableEntity.getLobbyEntity().getWantToPlayUsers().contains(UserId)
            ) {
            return true;
        }

        return false;
    }

    public void addUserInLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();

        gameTableEntity.getLobbyEntity().getWantToPlayUsers().add(UserId);

        gameTableRepository.save(gameTableEntity);
    }

    public void removeUserFromLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();

        gameTableEntity.getLobbyEntity().getWantToPlayUsers().remove(UserId);
        gameTableRepository.save(gameTableEntity);
    }

    public void startGame() {
        GameTableEntity gameTableEntity = getGameTable();

        if (
                !gameTableEntity.getIsGameContinues()
                && gameTableEntity.getLobbyEntity().getWantToPlayUsers().size() >= 2
        ) {

            gameTableEntity.setIsGameContinues(true);
            gameTableEntity.setIsFirstPlayerStep(true);


            PlayerEntity playerEntity = new PlayerEntity(
                    30,
                    10,
                    new ArrayList<Card>(),
                    new ArrayList<CardOnTableEntity>()
            );
            gameTableEntity.setFirstPlayer(playerEntity);
            gameTableEntity.setSecondPlayer(playerEntity);

            gameTableEntity.getLobbyEntity().setWinner("?");

            Random PRNG = new Random();
            int RandomNumber;

            RandomNumber = PRNG.nextInt(gameTableEntity.getLobbyEntity().getWantToPlayUsers().size());
            gameTableEntity.getLobbyEntity().setFirstPlayerId(
                    gameTableEntity.getLobbyEntity().getWantToPlayUsers().get(RandomNumber)
            );
            gameTableEntity.getLobbyEntity().getWantToPlayUsers().remove(RandomNumber);

            RandomNumber = PRNG.nextInt(gameTableEntity.getLobbyEntity().getWantToPlayUsers().size());
            gameTableEntity.getLobbyEntity().setSecondPlayerId(
                    gameTableEntity.getLobbyEntity().getWantToPlayUsers().get(RandomNumber)
            );
            gameTableEntity.getLobbyEntity().getWantToPlayUsers().remove(RandomNumber);


            gameTableEntity.getFirstPlayer().setCardsOnTable(new ArrayList<>());
            gameTableEntity.getFirstPlayer().setCardsOnHand(new ArrayList<>());
            gameTableEntity.getSecondPlayer().setCardsOnTable(new ArrayList<>());
            gameTableEntity.getSecondPlayer().setCardsOnHand(new ArrayList<>());

            gameTableRepository.save(gameTableEntity);


            for (int i = 0; i < 5; i++) {
                cardService.addCardInHand(true);
            }

            for (int i = 0; i < 5; i++) {
                cardService.addCardInHand(false);
            }

        }
    }
}
