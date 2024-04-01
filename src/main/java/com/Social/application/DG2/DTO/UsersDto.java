package com.Social.application.DG2.DTO;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class UsersDto {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private boolean gender;
    private String phoneNumber;
    private Timestamp dateOfBirth;
    private String mail;
    private String avatar;
    private boolean enable;
}
