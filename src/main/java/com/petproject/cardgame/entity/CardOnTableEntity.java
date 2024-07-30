package com.petproject.cardgame.entity;

import com.petproject.cardgame.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CardOnTableEntity {
    public Card type;
    public int hp;
    public int power;
    public Boolean canAttack;

    public void plusHp(int hp) {
        this.hp += hp;
    }
    public void plusPower(int power) {
        this.power += power;
    }
}
