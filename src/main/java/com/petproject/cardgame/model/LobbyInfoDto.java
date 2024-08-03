package com.petproject.cardgame.model;

import lombok.Data;

import java.util.List;

@Data
public class LobbyInfoDto {

    public String winner;
    public String firstPlayerId;
    public String secondPlayerId;
    public List<String> lobby;
}
