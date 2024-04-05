package com.Social.application.DG2.service;

import com.Social.application.DG2.dto.UsersDto;
import com.Social.application.DG2.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
     UserDetails loadUserByUsername(String username);
     UserDetails login(String username, String password);

     Users getUserByUsername(String username);

    ResponseEntity<String> addUser(UsersDto registerDTO);

}
