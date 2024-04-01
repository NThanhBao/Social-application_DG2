package com.Social.application.DG2.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class UsersDto {
    private String userName;
    private String password;
    private String mail;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private boolean gender;
    private Timestamp dateOfBirth;
//    private String avatar;
}
