package com.petproject.cardgame.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("game_table")
public class GameTableEntity {
    @Id
    public String id;

    public LobbyEntity lobby;

    public Boolean isGameContinues;
    public Boolean isFirstPlayerStep;

    public HoverEntity hover;

    public PlayerEntity firstPlayer;
    public PlayerEntity secondPlayer;

}
