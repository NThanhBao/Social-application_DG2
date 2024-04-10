package com.Social.application.DG2.repositories;

import com.Social.application.DG2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT COUNT(u) FROM Users u JOIN u.followingUser f WHERE f.username = :username")
    int countByFollowingUsersUsername(@Param("username") String username);
}