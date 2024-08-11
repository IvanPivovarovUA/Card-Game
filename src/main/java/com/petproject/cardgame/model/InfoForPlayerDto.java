package com.petproject.cardgame.model;

import com.petproject.cardgame.entity.HoverEntity;
import lombok.Data;

@Data
public class InfoForPlayerDto {
    public YourInfoDto yourInfoDto;
    public EnemyInfoDto enemyInfoDto;
    public Boolean isYourStep;
    public HoverEntity hover;
}
