package com.Social.application.DG2.service.Impl;


import com.Social.application.DG2.config.CustomUserDetails;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repository.UsersRepository;
import com.Social.application.DG2.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(users);
    }

    @Override
    public UserDetails login(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return userDetails;
    }

    @Override
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
