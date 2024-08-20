package com.petproject.cardgame.data.dto;

import com.petproject.cardgame.data.document.HoverDocument;
import lombok.Data;

@Data
public class InfoForPlayerDto {
    public YourInfoDto yourInfo;
    public EnemyInfoDto enemyInfo;
    public Boolean isYourStep;
    public HoverDocument hover;
}
