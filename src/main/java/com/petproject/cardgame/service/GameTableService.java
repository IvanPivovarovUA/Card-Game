package com.petproject.cardgame.service;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameTableService {

    @Autowired
    private GameTableRepository gameTableRepository;

    //override
    private GameTableEntity getGameTable() {
        Optional<GameTableEntity> gameTableEntityOptional = gameTableRepository.findById("1");

        if (gameTableEntityOptional.isEmpty()) {
            GameTableEntity gameTableEntity = new GameTableEntity();
            gameTableEntity.setId("1");
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
            (
                null != gameTableEntity.getFirstPlayer()
                && null != gameTableEntity.getFirstPlayer().getId()
                && gameTableEntity.getFirstPlayer().getId().equals(UserId)
            )
            ||
            (
                null != gameTableEntity.getSecondPlayer()
                && null != gameTableEntity.getSecondPlayer().getId()
                && gameTableEntity.getSecondPlayer().getId().equals(UserId)
            )
        )
        {
            return true;
        }

        return false;
    }

}
