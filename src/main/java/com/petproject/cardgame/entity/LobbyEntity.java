package com.petproject.cardgame.entity;

import lombok.Data;

import java.util.List;

@Data
public class LobbyEntity {

    public String winner;

    public String firstPlayerId;
    public String secondPlayerId;

    public List<String> wantToPlayUsers;
}
