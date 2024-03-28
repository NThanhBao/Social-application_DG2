package com.Social.application.DG2.dto;

import com.Social.application.DG2.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID usersId;
    private String username;
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
    private Timestamp createAt;
    private Timestamp updateAt;

    @ElementCollection
    @CollectionTable(name = "follows",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "following_user_id"})
    )
    @MapKeyJoinColumn(name = "following_user_id")
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Map<Users, Timestamp> followingUser = new HashMap<>();
}
