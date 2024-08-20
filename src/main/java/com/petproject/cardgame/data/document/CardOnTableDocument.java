package com.petproject.cardgame.data.document;

import com.petproject.cardgame.data.model.Card;
import lombok.Data;

@Data
public class CardOnTableDocument {
    private Integer id;
    private Card type;
    private Integer hp;
    private Integer power;
    private Boolean canAttack;

    public void plusHp(int hp) {
        this.hp += hp;
    }
    public void plusPower(int power) {
        this.power += power;
    }
}
