package com.petproject.cardgame.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public enum Card {
//    P(4, 0, 2), F(3, 0, 2), A(5, 0, 2), T(6, 0, 8),
    E(5,6, 7), z(2,0,3),

    P(4, 0, 2), S(3,0, 2), H(4, 0, 2), T(3, 0, 2),

    I(5, 0, 3), F(3, 0, 2), A(5, 0, 2), M(0, 0, 4),

    W(8, 10, 0), Z(6, 7, 4), K(5, 5, 5), V(2, 1, 3),

    B(6, 6, 6), R(7, 7, 7), r(5, 5, 4),

    L(4, 3, 6), l(3, 3, 4);

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

        for (int i = 0 ; i < 3;  i++) {
            cards.add(Card.K);
            cards.add(Card.K);
            cards.add(Card.L);
            cards.add(Card.B);
        }


        return cards.get(PRNG.nextInt(cards.size()));
    }
}
