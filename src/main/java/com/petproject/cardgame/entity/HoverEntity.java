package com.petproject.cardgame.entity;

import lombok.Data;

@Data
public class HoverEntity {
    Integer hand = -1;
    Integer table = -1;
    Integer enemy = -1;
    Boolean player = false;
}
