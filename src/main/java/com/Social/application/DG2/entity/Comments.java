package com.Social.application.DG2.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;

    public Comments() {
        this.id = UUID.randomUUID().toString();
    }

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts postId;

    @Column(name = "total_like")
    private int totalLike;

    @Column(name = "created_at")
    private Timestamp createAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Users createBy;
}

