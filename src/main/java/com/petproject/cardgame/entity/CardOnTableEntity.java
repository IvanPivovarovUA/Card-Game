package com.petproject.cardgame.entity;

import com.petproject.cardgame.model.Card;
import lombok.Data;

@Data
public class CardOnTableEntity {
    private Card type;
    private int hp;
    private int power;
    private Boolean canAttack;

    public void plusHp(int hp) {
        this.hp += hp;
    }
    public void plusPower(int power) {
        this.power += power;
    }
}
