package com.petproject.cardgame.model;

import com.petproject.cardgame.entity.CardOnTableEntity;
import com.petproject.cardgame.entity.HoverEntity;
import lombok.Data;

import java.util.List;

@Data
public class InfoForPlayer {
    public YourInfo yourInfo;
    public EnemyInfo enemyInfo;
    public Boolean isYourStep;
    public HoverEntity hover;

    public CardStats cardStats;
}
