package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.entity.UserLobbyEntity;
import com.petproject.cardgame.repository.GameTableRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

        Optional<GameTableEntity> gameTableEntityOptional = gameTableRepository.findById("669418f7ad9442136adfd4ef");

        if (gameTableEntityOptional.isEmpty()) {
            System.out.println("sfgsfgdfg");
            GameTableEntity gameTableEntity = new GameTableEntity();
//            gameTableEntity.setId("1");

            UserLobbyEntity userLobbyEntity = new UserLobbyEntity();
            userLobbyEntity.setWantToPlayUsers(new ArrayList<>());
            gameTableEntity.setUserLobbyEntity(userLobbyEntity);

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

    public void addUserInLobby() {
        String UserId = "Testfh";
//                getUserId();

        GameTableEntity gameTableEntity = getGameTable();
//                gameTableRepository.findById("1").get();


        gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().add(UserId);
        gameTableRepository.save(gameTableEntity);
    }
    public void removeUserFromLobby() {
        String UserId = "Test";
//                getUserId();

        GameTableEntity gameTableEntity = gameTableRepository.findById("1").get();

        gameTableEntity.getUserLobbyEntity().getWantToPlayUsers().remove(UserId);
    }


}
