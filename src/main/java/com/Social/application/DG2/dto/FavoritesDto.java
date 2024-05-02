package com.Social.application.DG2.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class FavoritesDto {
    private String postsId;
    private String usersId;
    private Timestamp createAt;
}
