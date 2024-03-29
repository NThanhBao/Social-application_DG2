package com.Social.application.DG2.controller;

import com.Social.application.DG2.config.JwtTokenUtil;
import com.Social.application.DG2.dto.UsersDto;
import com.Social.application.DG2.dto.UsersInfoDto;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repository.UsersRepository;
import com.Social.application.DG2.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    private UsersService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        try{
            UserDetails userDetails = userService.login(username, password);
            String token = jwtTokenUtil.generateToken(username);
            Users users = userService.getUserByUsername(username);

            UsersInfoDto usersInfoDto = modelMapper.map(users, UsersInfoDto.class);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token: ", token);
            responseData.put("userInfo: ", usersInfoDto);

                return  ResponseEntity.ok(responseData);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
