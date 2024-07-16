package com.petproject.cardgame.entity;

import lombok.Data;

import java.util.List;

@Data
public class LobbyEntity {

    public String Winner;

    public String FirstPlayerId;
    public String SecondPlayerId;

    public List<String> WantToPlayUsers;
}
