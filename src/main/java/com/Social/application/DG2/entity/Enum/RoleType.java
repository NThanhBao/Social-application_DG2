package com.Social.application.DG2.entity.Enum;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN(1),
    USER(2);

    private final int id;

    RoleType(int id) {
        this.id = id;
    }

}
