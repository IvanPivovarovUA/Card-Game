package com.petproject.cardgame.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("gametable")
public class GameTableEntity {
    @Id
    private String Id;

    private UserLobbyEntity userLobbyEntity;

    private Boolean IsGameContinues;
    private Boolean IsFirstPlayerStep;
    private PlayerEntity FirstPlayer;
    private PlayerEntity SecondPlayer;

}
