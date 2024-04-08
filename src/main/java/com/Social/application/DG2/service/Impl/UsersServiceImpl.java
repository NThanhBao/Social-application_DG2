package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.dto.UsersDto;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import com.Social.application.DG2.config.CustomUserDetails;
import com.Social.application.DG2.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository registerRepository;
    @Autowired
    private PasswordEncoder encoder;
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
    public ResponseEntity<String> addUser(UsersDto registerDTO) {
        if (registerRepository.existsByUsername(registerDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username đã đợc sử dụng.");
        }
        if (registerRepository.existsByMail(registerDTO.getMail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email đã được sử dụng.");
        }
        if (registerRepository.existsByPhoneNumber(registerDTO.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số điện thoại đã được sử dụng.");
        }
        Users user = new Users();

        user.setUsername(registerDTO.getUsername());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setGender(registerDTO.isGender());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setDateOfBirth(registerDTO.getDateOfBirth());
        user.setMail(registerDTO.getMail());

        registerRepository.save(user);

        return ResponseEntity.ok("Tạo thành công với username: " + user.getUsername());
    }

    @Override
    public UserDetails login(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if(!encoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return userDetails;
    }

    @Override
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}