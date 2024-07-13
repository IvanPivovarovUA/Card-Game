package com.petproject.cardgame.model;

import lombok.Getter;

import java.util.Random;

@Getter
public enum Card {
    H(4, 2, 0), P(4, 0, 2), F(3, 0, 2), A(5, 0, 2), T(6, 0, 8),

    W(8, 10, 1), Z(6, 7, 4), K(5, 5, 5), V(2, 1, 3),

    B(6, 6, 6), R(7, 7, 7), r(5, 5, 4),

    L(4, 3, 6), l(3, 3, 4);

    private int Mana;
    private int Hp;
    private int Power;

    Card(int Mana, int Hp, int Power) {
        this.Mana = Mana;
        this.Hp = Hp;
        this.Power = Power;
    }

    private static final Random PRNG = new Random();

    public static Card randomCard()  {
        Card[] cards = values();
        return cards[PRNG.nextInt(cards.length)];
    }
}
