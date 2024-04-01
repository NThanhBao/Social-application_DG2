package com.Social.application.DG2.controller;

import com.Social.application.DG2.DTO.UsersDto;
import com.Social.application.DG2.service.Impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/auth")
public class UsersController {

    @Autowired
    private UsersServiceImpl registerService;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UsersDto registerDTO) {
        return registerService.addUser(registerDTO);
    }
}