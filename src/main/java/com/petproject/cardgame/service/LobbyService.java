package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.UserLobbyEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LobbyService {

    @Autowired
    private GameTableRepository gameTableRepository;

    //override
    private GameTableEntity getGameTable() {
        Optional<GameTableEntity> gameTableEntityOptional = gameTableRepository.findById("1");

        if (gameTableEntityOptional.isEmpty()) {
            GameTableEntity gameTableEntity = new GameTableEntity();
            gameTableEntity.setId("1");
            gameTableEntity.setUserLobbyEntity(new UserLobbyEntity());

            gameTableRepository.save(gameTableEntity);
            return gameTableEntity;
        }
        else {
            return gameTableEntityOptional.get();
        }
    }

    private String getUserId() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    public boolean isUserInGame() {
        GameTableEntity gameTableEntity = getGameTable();
        String UserId = getUserId();

        if (
            null != gameTableEntity.getUserLobbyEntity().getFirstPlayerId()
            && gameTableEntity.getUserLobbyEntity().getFirstPlayerId().equals(UserId)
        ) {
            return true;
        }

        if (
            null != gameTableEntity.getUserLobbyEntity().getSecondPlayerId()
            && gameTableEntity.getUserLobbyEntity().getSecondPlayerId().equals(UserId)
        ) {
            return true;
        }

        return false;
    }

    public void removeUserFromLobby(String UserId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().remove(UserId);
    }

    public void addUserInLobby(String newUserId) {
        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().add(newUserId);
    }
}
