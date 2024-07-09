package com.petproject.cardgame.entity;

import com.petproject.cardgame.entity.Card.CardEntity;

import java.util.List;

public class PlayerEntity {
    private String Id;
    private int HP;// 0 - 25
    private int Mana;// 0 - 10
    private List<CardEntity> CardsOnHand;
    private List<CardEntity> CardsOnTable;

    public String getId() {
        return Id;
    }
}
