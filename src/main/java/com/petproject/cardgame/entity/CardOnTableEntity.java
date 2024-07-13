package com.petproject.cardgame.entity;

import com.petproject.cardgame.model.Card;
import lombok.Data;

@Data
public class CardOnTableEntity {
    private Card Type;
    private int Hp;
    private int Power;
    private Boolean CanAttack;

    public void plusHp(int Hp) {
        this.Hp += Hp;
    }
    public void plusPower(int Power) {
        this.Power += Power;
    }
}
