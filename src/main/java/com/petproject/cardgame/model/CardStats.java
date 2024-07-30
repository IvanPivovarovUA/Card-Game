package com.petproject.cardgame.model;

public class CardStats {
    public String type;
    public int mana;
    public int hp;
    public int power;

    public CardStats(Card card) {
        this.type = card.name();
        this.mana = card.getMana();
        this.hp = card.getHp();
        this.power = card.getPower();
    }

}
