package com.Social.application.DG2.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;


import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "reactions")
public class Reactions {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private UUID reactionsId;

    @Column(name = "object_type")
    private String objectType;

    @Column(name = "object_id", columnDefinition = "CHAR(36)")

    private UUID objectId;

    private int type;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Users usersId;
}
