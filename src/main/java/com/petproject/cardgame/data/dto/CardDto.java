package com.petproject.cardgame.data.dto;

import com.petproject.cardgame.data.document.CardOnTableDocument;
import com.petproject.cardgame.data.model.Card;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;


public class CardDto {
    public int id;
    public String type;
    public int mana;
    public int hp;
    public int power;
    public Boolean canAttack;

    public CardDto(Card card) {
        this.type = card.name();
        this.mana = card.getMana();
        this.hp = card.getHp();
        this.power = card.getPower();
    }

    public CardDto(CardOnTableDocument cardOnTableDocument) {
        this.type = cardOnTableDocument.getType().name();
//        this.mana = cardOnTableDocument.getMana();
        this.hp = cardOnTableDocument.getHp();
        this.power = cardOnTableDocument.getPower();
        this.canAttack = cardOnTableDocument.getCanAttack();

        if (cardOnTableDocument.getId() != null) {
            this.id = cardOnTableDocument.getId();
        }

    }

}
