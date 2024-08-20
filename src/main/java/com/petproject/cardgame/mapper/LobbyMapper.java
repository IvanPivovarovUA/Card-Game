package com.petproject.cardgame.mapper;

import com.petproject.cardgame.data.document.GameTableDocument;
import com.petproject.cardgame.data.dto.LobbyInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class LobbyMapper {
    static public LobbyInfoDto gametTableToLobbyInfoDto(GameTableDocument gameTableDocument) {
        LobbyInfoDto lobbyInfoDto = new LobbyInfoDto();

        lobbyInfoDto.setWinner(gameTableDocument.getWinner());

        if (
                gameTableDocument.getFirstPlayer() != null
                        && gameTableDocument.getFirstPlayer().getUserInfo().getId() != null
        ) {
            lobbyInfoDto.setFirstPlayerId(
                    gameTableDocument
                            .getFirstPlayer()
                            .getUserInfo()
                            .getNickname()
            );
        }
        else {
            lobbyInfoDto.setFirstPlayerId("");
        }

        if (
                gameTableDocument.getSecondPlayer() != null
                        && gameTableDocument.getSecondPlayer().getUserInfo().getId() != null
        ) {
            lobbyInfoDto.setSecondPlayerId(
                    gameTableDocument
                            .getSecondPlayer()
                            .getUserInfo()
                            .getNickname()
            );
        }
        else {
            lobbyInfoDto.setSecondPlayerId("");
        }

        lobbyInfoDto.setLobby(
                gameTableDocument.getLobby()
                        .stream()
                        .map(i -> i.getNickname())
                        .toList()
        );

        return lobbyInfoDto;
    }

}
