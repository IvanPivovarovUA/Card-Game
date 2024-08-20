package com.petproject.cardgame.data.document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDocument {
    private String id;
    private String nickname;
}
