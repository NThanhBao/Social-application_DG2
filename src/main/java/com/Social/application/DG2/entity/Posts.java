package com.Social.application.DG2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "posts")
public class Posts {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    public Posts() {
        this.id = UUID.randomUUID().toString();
    }


    private String title;

    @Column
    @NotNull
    private String body;

    private String status;

    @Column(name = "total_like")
    private int totalLike;

    @Column(name = "total_comment")
    private int totalComment;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Users userId;

    @Column(name = "created_at")
    private Timestamp createAt;
}
