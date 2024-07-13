package com.petproject.cardgame.entity;

import com.petproject.cardgame.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlayerEntity {

    private int Hp;// 0 - 25
    private int Mana;// 0 - 10
    private List<Card> CardsOnHand;
    private List<CardOnTableEntity> CardsOnTable;

    public void plusHp(int Hp) {
        this.Hp += Hp;
    }
    public void plusMana(int Mana) {
        this.Mana += Mana;
    }
}
