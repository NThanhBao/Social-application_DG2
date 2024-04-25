package com.Social.application.DG2.repositories;

import com.Social.application.DG2.entity.Reactions;
import com.Social.application.DG2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionsRepository extends JpaRepository<Reactions, String> {
    List<Reactions> findAllByObjectIdAndType(String objectId, String type);
    Optional<Reactions> findByCreatedByAndObjectIdAndType(Users createdBy, String objectId, String type);
    List<Reactions> findByObjectId(String object_id);
    List<Reactions> findByObjectIdAndType(String objectId, String type);
}
