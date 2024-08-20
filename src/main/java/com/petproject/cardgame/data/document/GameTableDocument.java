package com.petproject.cardgame.data.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("game_table")
public class GameTableDocument {
    @Id
    private String id;

    private Integer idForCards;

    private String winner;
    private List<UserInfoDocument> lobby;

    private Boolean isGameContinues;
    private Boolean isFirstPlayerStep;

    private HoverDocument hover;

    private PlayerDocument firstPlayer;
    private PlayerDocument secondPlayer;

    public GameTableDocument() {
        this.id = "1";
        this.idForCards = 0;

        this.setWinner("?");
        this.lobby = new ArrayList<>();

        this.isGameContinues = false;
    }

    //MyGetter
    public int getIdForCards() {
        this.idForCards += 1;
        return this.idForCards;
    }
}
