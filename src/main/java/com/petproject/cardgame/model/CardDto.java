package com.petproject.cardgame.model;

public class CardDto {
    public String type;
    public int mana;
    public int hp;
    public int power;
//    public boolean isEnoughMana;

    public CardDto(Card card) {
        this.type = card.name();
        this.mana = card.getMana();
        this.hp = card.getHp();
        this.power = card.getPower();
    }

}
