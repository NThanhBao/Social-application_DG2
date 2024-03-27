package com.Social.application.DG2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "medias")
public class Medias {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID mediasId;

    @Column(name = "base_name")
    private String baseName;

    @Column(name = "public_url")
    private String publicUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts postsId;
}
