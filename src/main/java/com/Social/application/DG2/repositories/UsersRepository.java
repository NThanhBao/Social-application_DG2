package com.Social.application.DG2.repositories;

import com.Social.application.DG2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    boolean existsByUsername(String username);
}