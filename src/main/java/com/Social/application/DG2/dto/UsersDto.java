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
public class UsersDto {

    private String userName;
    private String password;
    private String mail;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private boolean gender;
    private Timestamp dateOfBirth;
    private String avatar;
    private boolean enable;
    private Timestamp createAt;
    private Timestamp updateAt;

}
