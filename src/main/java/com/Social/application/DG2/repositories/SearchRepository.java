package com.Social.application.DG2.repositories;

import com.Social.application.DG2.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Users, String> {

    @Query("SELECT u FROM Users u WHERE (u.firstName LIKE %?1% " +
            "OR u.lastName LIKE %?1% " +
            "OR CONCAT(u.firstName, ' ', u.lastName) LIKE %?1%" +
            "OR CONCAT(u.lastName, ' ', u.firstName) LIKE %?1%)" +
            "AND (u.roleType <> 'ADMIN' OR u.roleType IS NULL)")
    Page<Users> findByFullNameAndNotAdminRole(String fullName, Pageable pageable);
}
