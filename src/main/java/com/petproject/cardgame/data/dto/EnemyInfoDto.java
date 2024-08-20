package com.petproject.cardgame.data.dto;

import com.petproject.cardgame.data.document.CardOnTableDocument;
import lombok.Data;

import java.util.List;

@Data
public class EnemyInfoDto {

    public Integer hand;
    public List<CardOnTableDocument> table;
    public List<CardDto> dropedCards;

    public String nickname;

    private Integer hp;
    private Integer mana;

}
