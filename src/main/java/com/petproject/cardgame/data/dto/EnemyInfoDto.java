package com.petproject.cardgame.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnemyInfoDto {

    public Integer hand;
    public List<CardDto> table;
    public List<CardDto> droppedCards;

    public String nickname;

    private Integer hp;
    private Integer mana;

}
