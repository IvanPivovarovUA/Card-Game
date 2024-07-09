package com.petproject.cardgame.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("gametable")
public class GameTableEntity {
    @Id
    private String Id;

    private boolean isGameContinues;
    private boolean isFirstPlayerStep;
    private String Winner;
    private PlayerEntity FirstPlayer;
    private PlayerEntity SecondPlayer;

    public PlayerEntity getFirstPlayer() {
        return FirstPlayer;
    }
    public PlayerEntity getSecondPlayer() {
        return SecondPlayer;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
