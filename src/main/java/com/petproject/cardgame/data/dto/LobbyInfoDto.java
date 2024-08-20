package com.petproject.cardgame.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class LobbyInfoDto {

    public String winner;
    public String firstPlayerId;
    public String secondPlayerId;
    public List<String> lobby;
}
