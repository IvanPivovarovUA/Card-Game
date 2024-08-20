package com.petproject.cardgame.data.dto;

import com.petproject.cardgame.data.document.CardOnTableDocument;
import lombok.Data;

import java.util.List;

@Data
public class YourInfoDto {

    public List<CardDto> hand;
    public List<CardOnTableDocument> table;
    public List<CardDto> dropedCards;

    public String nickname;

    public Integer hp;
    public Integer mana;

}
