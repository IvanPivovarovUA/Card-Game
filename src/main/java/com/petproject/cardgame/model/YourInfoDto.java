package com.petproject.cardgame.model;

import com.petproject.cardgame.entity.CardOnTableEntity;
import lombok.Data;

import java.util.List;

@Data
public class YourInfoDto {

    public List<CardDto> hand;
    public List<CardOnTableEntity> table;
    public List<CardDto> dropedCards;

    public String nickname;

    public Integer hp;
    public Integer mana;

}
