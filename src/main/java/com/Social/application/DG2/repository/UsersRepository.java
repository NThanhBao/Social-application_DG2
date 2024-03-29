package com.Social.application.DG2.repository;

import com.Social.application.DG2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);
}
