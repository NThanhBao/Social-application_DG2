package com.Social.application.DG2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersInfoDto {
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private boolean gender;
    private String phoneNumber;
    private Timestamp dateOfBirth;
    private String mail;
    private String avatar;

}
