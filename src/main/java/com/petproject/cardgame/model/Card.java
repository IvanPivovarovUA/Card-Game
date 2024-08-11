package com.petproject.cardgame.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public enum Card {


    z(7,0,0), T(8, 0, 6),

    P(4, 0, 0), S(4,0, 2), H(4, 0, 2),

    ////////////////////////////////////// I ~ power

    F(5, 0, 3), A(5, 0, 2),

    I(4, 0, 3), M(0, 0, 4),

    //////////////////////////////////////

    W(8, 10, 0), Z(6, 6, 4), m(5,5,5),

    //////////////////////////////////////
    E(6,6, 6), K(5, 5, 5), V(3, 1, 3),

    B(6, 6, 7), R(8, 8, 8), r(5, 6, 5),

    L(5, 4, 5), l(3, 2, 4),  t(5,5,6);

    private int mana;
    private int hp;
    private int power;

    Card(int mana, int hp, int power) {
        this.mana = mana;
        this.hp = hp;
        this.power = power;
    }

    private static final Random PRNG = new Random();

    public static Card randomCard()  {

        List<Card> cards = new ArrayList<>();
        for (Card card : values()) {
            cards.add(card);
        }

//        for (int i = 0 ; i < 2;  i++) {
//        cards.add(Card.K);
//        cards.add(Card.E);
//        cards.add(Card.L);
//        cards.add(Card.B);
//        }

        return cards.get(PRNG.nextInt(cards.size()));
    }

    public static List<Card> getArmorCards() {
        List<Card> armorCards = new ArrayList<>();
        armorCards.add(W);
        armorCards.add(Z);
        armorCards.add(E);

        return armorCards;
    }

}
