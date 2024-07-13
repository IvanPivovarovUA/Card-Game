package com.petproject.cardgame.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserLobbyEntity {

    private String Winner;

    private String FirstPlayerId;
    private String SecondPlayerId;

    private List<String> WantToPlayUsers;
}
