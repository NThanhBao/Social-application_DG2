package com.Social.application.DG2.controller;

import com.Social.application.DG2.dto.UsersDto;
import com.Social.application.DG2.service.Impl.UsersServiceImpl;
import com.Social.application.DG2.util.annotation.CheckEnableType;
import com.Social.application.DG2.util.annotation.CheckLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Social.application.DG2.config.JwtTokenUtil;
import com.Social.application.DG2.dto.UsersInfoDto;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class UsersController {
    @Autowired
    private UsersService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsersServiceImpl registerService;

    @CheckEnableType
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpServletResponse response) {
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

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody UsersDto registerDTO) {
        return registerService.addUser(registerDTO);
    }

    @CheckLogin
    @GetMapping("/checktoken")
    public ResponseEntity<String> protectedApi(HttpServletRequest request) {
        return ResponseEntity.ok("chỉ khi JWT đúng thì mới xem được thông tin này");
    }
}

