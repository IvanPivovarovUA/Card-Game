package com.petproject.cardgame.model;

import com.petproject.cardgame.entity.CardOnTableEntity;
import lombok.Data;

import java.util.List;

@Data
public class InfoForPlayer {
    public List<Card> yourHand;
    public List<CardOnTableEntity> yourTable;

    public List<CardOnTableEntity>  enemyTable;
    public Integer enemyHand;

    public Boolean isYourStep;

    public String yourNickname;
    public String enemyNickname;
}
