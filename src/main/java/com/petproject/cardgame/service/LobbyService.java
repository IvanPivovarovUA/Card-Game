package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.LobbyEntity;
import com.petproject.cardgame.repository.GameTableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LobbyService {

    @Autowired
    private GameTableRepository gameTableRepository;

    //override
    public GameTableEntity getGameTable() {

        Optional<GameTableEntity> gameTableEntityOptional = gameTableRepository.findById("1");

        if (gameTableEntityOptional.isEmpty()) {
            GameTableEntity gameTableEntity = new GameTableEntity();
            gameTableEntity.setId("1");

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

//    public String getUserId() {
//        return SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//    }

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


}
