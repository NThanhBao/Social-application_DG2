package com.Social.application.DG2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private UUID usersId;

    @Column(name = "username")
    private String userName;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String role;

    private boolean gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    private String mail;

    private String avatar;

    private boolean enable;

    @Column(name = "created_at")
    private Timestamp createAt;

    @Column(name = "updated_at")
    private Timestamp updateAt;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "follows",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "following_user_id")
//    )
//    private Set<Users> followingUser = new HashSet<>();


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
