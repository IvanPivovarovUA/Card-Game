package com.petproject.cardgame.data.document;

import lombok.Data;

@Data
public class HoverDocument {
    private Integer hand;
    private Integer table;
    private Integer enemy;
    private Boolean player;

    public HoverDocument() {
        this.hand = -1;
        this.table = -1;
        this.enemy = -1;
        this.player = false;
    }
}
