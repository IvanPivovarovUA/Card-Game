package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.*;
import com.petproject.cardgame.repository.GameTableRepository;
import com.petproject.cardgame.service.game_process.UseCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petproject.cardgame.model.Card;

import java.util.ArrayList;
import java.util.Random;
import java.util.Optional;


@Service
public class LobbyService {

    @Autowired
    private GameTableRepository gameTableRepository;

    public GameTableEntity getGameTable() {
        Optional<GameTableEntity> gameTableEntityOptional = gameTableRepository.findById("1");

        if (gameTableEntityOptional.isEmpty()) {
            GameTableEntity gameTableEntity = new GameTableEntity();
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
                null != gameTableEntity.getFirstPlayer()
            && null != gameTableEntity.getFirstPlayer().getId()
            && gameTableEntity.getFirstPlayer().getId().equals(UserId)
        ) {
            return true;
        }

        if (
                null != gameTableEntity.getSecondPlayer()
            && null != gameTableEntity.getSecondPlayer().getId()
            && gameTableEntity.getSecondPlayer().getId().equals(UserId)
        ) {
            return true;
        }

        return false;
    }

    public boolean isUserInLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();
        if (
                gameTableEntity.getLobby().contains(UserId)
            ) {
            return true;
        }

        return false;
    }

    public void addUserInLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();
        gameTableEntity.getLobby().add(UserId);
        gameTableRepository.save(gameTableEntity);
    }

    public void removeUserFromLobby(String UserId) {
        GameTableEntity gameTableEntity = getGameTable();
        gameTableEntity.getLobby().remove(UserId);
        gameTableRepository.save(gameTableEntity);
    }

    public boolean canIStartGame() {
        GameTableEntity gameTableEntity = getGameTable();
        return !gameTableEntity.getIsGameContinues()
                && gameTableEntity.getLobby().size() >= 2;
    }

    public void startGame() {
        GameTableEntity gameTableEntity = getGameTable();

        if (
                !gameTableEntity.getIsGameContinues()
                && gameTableEntity.getLobby().size() >= 2
        ) {

            gameTableEntity.setIsGameContinues(true);
            gameTableEntity.setIsFirstPlayerStep(true);
            gameTableEntity.setHover(new HoverEntity());
            gameTableEntity.setWinner("?");


            Random PRNG = new Random();
            int RandomNumber;

            RandomNumber = PRNG.nextInt(gameTableEntity.getLobby().size());
            gameTableEntity.setFirstPlayer(
                new PlayerEntity(
                        gameTableEntity.getLobby().get(RandomNumber),
                        30,
                        10,
                        new ArrayList<Card>(),
                        new ArrayList<CardOnTableEntity>()
                )
            );
            gameTableEntity.getLobby().remove(RandomNumber);

            RandomNumber = PRNG.nextInt(gameTableEntity.getLobby().size());
            gameTableEntity.setSecondPlayer(
                new PlayerEntity(
                        gameTableEntity.getLobby().get(RandomNumber),
                        30,
                        10,
                        new ArrayList<Card>(),
                        new ArrayList<CardOnTableEntity>()
                )
            );
            gameTableEntity.getLobby().remove(RandomNumber);

            gameTableRepository.save(gameTableEntity);
        }
    }
}
