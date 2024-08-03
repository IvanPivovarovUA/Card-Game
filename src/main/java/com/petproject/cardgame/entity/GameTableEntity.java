package com.petproject.cardgame.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("game_table")
public class GameTableEntity {
    @Id
    private String id;

    private String winner;
    private List<String> lobby;

    private Boolean isGameContinues;
    private Boolean isFirstPlayerStep;

    private HoverEntity hover;

    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;

    public GameTableEntity() {
        this.setId("1");
        this.setWinner("?");
        this.setIsGameContinues(false);
        this.setLobby(new ArrayList<>());
    }
}
