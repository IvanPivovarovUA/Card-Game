package com.petproject.cardgame.entity;

import lombok.Data;

@Data
public class HoverEntity {
    Integer hand;
    Integer table;
    Integer enemy;
    Boolean player;

    public HoverEntity() {
        this.hand = -1;
        this.table = -1;
        this.enemy = -1;
        this.player = false;
    }





}
