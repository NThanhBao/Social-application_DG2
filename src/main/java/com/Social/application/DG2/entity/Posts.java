package com.Social.application.DG2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "posts")
public class Posts {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private UUID postsId;


    private String title;

    private String body;

    private String status;

    @Column(name = "total_like")
    private int totalLike;

    @Column(name = "total_comment")
    private int totalComment;

    @ManyToOne
    @JoinColumn(name = "created_by", unique = true)
    private Users usersId;

    @Column(name = "created_at")
    private Timestamp createAt;
}
