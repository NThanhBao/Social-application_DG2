package com.Social.application.DG2.repositories;

import com.Social.application.DG2.entity.Medias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Medias, String> {
    @Query("SELECT m FROM Medias m")
    List<Medias> findAllMedias();
}
