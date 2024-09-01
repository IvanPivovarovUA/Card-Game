package com.petproject.cardgame.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class YourInfoDto {

    public List<CardDto> hand;
    public List<CardDto> table;
    public List<CardDto> droppedCards;

    public String nickname;

    public Integer hp;
    public Integer mana;

}
