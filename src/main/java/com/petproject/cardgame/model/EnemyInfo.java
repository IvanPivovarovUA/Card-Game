package com.petproject.cardgame.model;

import com.petproject.cardgame.entity.CardOnTableEntity;
import lombok.Data;

import java.util.List;

@Data
public class EnemyInfo {

    public Integer hand;
    public List<CardOnTableEntity> table;

    public String nickname;

    private Integer hp;
    private Integer mana;

}
