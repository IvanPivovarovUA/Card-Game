package com.petproject.cardgame.entity;

import com.petproject.cardgame.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlayerEntity {

    public String id;

    private int hp;// 0 - 25
    private int mana;// 0 - 10
    private List<Card> cardsOnHand;
    private List<CardOnTableEntity> cardsOnTable;

    public void plusHp(int hp) {
        this.hp += hp;
    }
    public void plusMana(int mana) {
        this.mana += mana;
    }
}
