package com.petproject.cardgame.service;

import com.petproject.cardgame.data.document.*;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petproject.cardgame.data.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;


@Service
public class LobbyService {

    @Autowired
    private GameTableRepository gameTableRepository;

    public GameTableDocument getGameTable() {
        Optional<GameTableDocument> gameTableEntityOptional = gameTableRepository.findById("1");

        if (gameTableEntityOptional.isEmpty()) {
            GameTableDocument gameTableDocument = new GameTableDocument();
            gameTableRepository.save(gameTableDocument);

            return gameTableDocument;
        }
        else {
            return gameTableEntityOptional.get();
        }
    }

    public boolean isGameContinues() {
        GameTableDocument gameTableDocument = getGameTable();
        return gameTableDocument.getIsGameContinues();
    }

    public boolean isUserInGame(String UserId) {
        GameTableDocument gameTableDocument = getGameTable();

        if (
                null != gameTableDocument.getFirstPlayer()
            && null != gameTableDocument.getFirstPlayer().getUserInfo().getId()
            && gameTableDocument.getFirstPlayer().getUserInfo().getId().equals(UserId)
        ) {
            return true;
        }

        if (
                null != gameTableDocument.getSecondPlayer()
            && null != gameTableDocument.getSecondPlayer().getUserInfo().getId()
            && gameTableDocument.getSecondPlayer().getUserInfo().getId().equals(UserId)
        ) {
            return true;
        }

        return false;
    }

    public boolean isUserInLobby(String UserId) {
        GameTableDocument gameTableDocument = getGameTable();

        for (UserInfoDocument userInfoDocument: gameTableDocument.getLobby()) {
            if (
                    userInfoDocument.getId().equals(UserId)
            ) {
                return true;
            }
        }

        return false;
    }

    public void addUserInLobby(String userId, String userNickName) {
        GameTableDocument gameTableDocument = getGameTable();

        gameTableDocument.getLobby().add(
                new UserInfoDocument(userId, userNickName)
        );

        gameTableRepository.save(gameTableDocument);
    }

    public void removeUserFromLobby(String userId, String userNickName) {
        GameTableDocument gameTableDocument = getGameTable();
        gameTableDocument.getLobby().remove(
                new UserInfoDocument(userId, userNickName)
        );
        gameTableRepository.save(gameTableDocument);
    }

    public boolean canIStartGame() {
        GameTableDocument gameTableDocument = getGameTable();
        return !gameTableDocument.getIsGameContinues()
                && gameTableDocument.getLobby().size() >= 2;
    }

    public void startGame() {
        GameTableDocument gameTableDocument = getGameTable();

        if (
                !isGameContinues()
                && gameTableDocument.getLobby().size() >= 2
        ) {

            gameTableDocument.setIsGameContinues(true);
            gameTableDocument.setIsFirstPlayerStep(true);
            gameTableDocument.setHover(new HoverDocument());
            gameTableDocument.setWinner("?");


            List<UserInfoDocument> lobby =  new ArrayList<>(gameTableDocument.getLobby());
            Random PRNG = new Random();
            int RandomNumber;

            RandomNumber = PRNG.nextInt(lobby.size());
            gameTableDocument.setFirstPlayer(
                new PlayerDocument(
                        lobby.get(RandomNumber),
                        30,
                        10,
                        new ArrayList<Card>(),
                        new ArrayList<CardOnTableDocument>(),
                        new ArrayList<Card>()
                )
            );
            lobby.remove(RandomNumber);

            RandomNumber = PRNG.nextInt(lobby.size());
            gameTableDocument.setSecondPlayer(
                new PlayerDocument(
                        lobby.get(RandomNumber),
                        30,
                        10,
                        new ArrayList<Card>(),
                        new ArrayList<CardOnTableDocument>(),
                        new ArrayList<Card>()
                )
            );
            lobby.remove(RandomNumber);

            gameTableRepository.save(gameTableDocument);
        }
    }
}
