package com.petproject.cardgame.data.document;

import com.petproject.cardgame.data.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlayerDocument {

    private UserInfoDocument userInfo;

    private Integer hp;// 0 - 25
    private Integer mana;// 0 - 10
    private List<Card> cardsOnHand;
    private List<CardOnTableDocument> cardsOnTable;
    private List<Card> dropedSpells;

    public void plusHp(int hp) {
        this.hp += hp;
    }
    public void plusMana(int mana) {
        this.mana += mana;
    }
}
