package com.petproject.cardgame.model;

import com.petproject.cardgame.entity.HoverEntity;
import lombok.Data;

@Data
public class InfoForPlayerDto {
    public YourInfo yourInfo;
    public EnemyInfo enemyInfo;
    public Boolean isYourStep;
    public HoverEntity hover;
}
