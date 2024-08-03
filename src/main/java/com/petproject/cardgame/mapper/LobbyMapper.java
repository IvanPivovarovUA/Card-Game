package com.petproject.cardgame.mapper;

import com.petproject.cardgame.entity.GameTableEntity;
import com.petproject.cardgame.model.LobbyInfoDto;

public class LobbyMapper {
    static public LobbyInfoDto gametTableToLobbyInfoDto(GameTableEntity gameTableEntity) {
        LobbyInfoDto lobbyInfoDto = new LobbyInfoDto();

        lobbyInfoDto.setWinner(gameTableEntity.getWinner());

        if (
                gameTableEntity.getFirstPlayer() != null
                        && gameTableEntity.getFirstPlayer().getId() != null
        ) {
            lobbyInfoDto.setFirstPlayerId(gameTableEntity.getFirstPlayer().getId());
        }
        else {
            lobbyInfoDto.setFirstPlayerId("");
        }

        if (
                gameTableEntity.getSecondPlayer() != null
                        && gameTableEntity.getSecondPlayer().getId() != null
        ) {
            lobbyInfoDto.setSecondPlayerId(gameTableEntity.getSecondPlayer().getId());
        }
        else {
            lobbyInfoDto.setSecondPlayerId("");
        }

        lobbyInfoDto.setLobby(gameTableEntity.getLobby());

        return lobbyInfoDto;
    }
}
