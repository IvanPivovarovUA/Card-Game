package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.LobbyEntity;
import com.petproject.cardgame.entity.PlayerEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.game_table.CardService;
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
            gameTableEntity.setLobby(lobbyEntity);

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
            null != gameTableEntity.getLobby().getFirstPlayerId()
            && gameTableEntity.getLobby().getFirstPlayerId().equals(UserId)
        ) {
            return true;
        }

        if (
            null != gameTableEntity.getLobby().getSecondPlayerId()
            && gameTableEntity.getLobby().getSecondPlayerId().equals(UserId)
        ) {
            return true;
        }

        return false;
    }

    public boolean isUserInLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();
        if (
                gameTableEntity.getLobby().getWantToPlayUsers().contains(UserId)
            ) {
            return true;
        }

        return false;
    }

    public void addUserInLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();

        gameTableEntity.getLobby().getWantToPlayUsers().add(UserId);

        gameTableRepository.save(gameTableEntity);
    }

    public void removeUserFromLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();

        gameTableEntity.getLobby().getWantToPlayUsers().remove(UserId);
        gameTableRepository.save(gameTableEntity);
    }

    public void startGame() {
        GameTableEntity gameTableEntity = getGameTable();

        if (
                !gameTableEntity.getIsGameContinues()
                && gameTableEntity.getLobby().getWantToPlayUsers().size() >= 2
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

            gameTableEntity.getLobby().setWinner("?");

            Random PRNG = new Random();
            int RandomNumber;

            RandomNumber = PRNG.nextInt(gameTableEntity.getLobby().getWantToPlayUsers().size());
            gameTableEntity.getLobby().setFirstPlayerId(
                    gameTableEntity.getLobby().getWantToPlayUsers().get(RandomNumber)
            );
            gameTableEntity.getLobby().getWantToPlayUsers().remove(RandomNumber);

            RandomNumber = PRNG.nextInt(gameTableEntity.getLobby().getWantToPlayUsers().size());
            gameTableEntity.getLobby().setSecondPlayerId(
                    gameTableEntity.getLobby().getWantToPlayUsers().get(RandomNumber)
            );
            gameTableEntity.getLobby().getWantToPlayUsers().remove(RandomNumber);


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
