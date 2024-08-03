package com.petproject.cardgame.model;

import com.petproject.cardgame.entity.CardOnTableEntity;
import lombok.Data;

import java.util.List;

@Data
public class YourInfo {

    public List<CardDto> hand;
    public List<CardOnTableEntity> table;

    public String nickname;

    public Integer hp;
    public Integer mana;

}
