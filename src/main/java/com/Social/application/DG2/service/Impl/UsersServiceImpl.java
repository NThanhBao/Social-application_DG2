package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.DTO.UsersDto;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository registerRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String addUser(UsersDto registerDTO) {
        if (registerRepository.existsByUsername(registerDTO.getUserName())) {
            return "Username đã tồn tại";
        }

        Users user = new Users();


        user.setUsername(registerDTO.getUserName());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setRole(registerDTO.getRole());
        user.setGender(registerDTO.isGender());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setDateOfBirth(registerDTO.getDateOfBirth());
        user.setMail(registerDTO.getMail());
        user.setEnable(registerDTO.isEnable());

        registerRepository.save(user);

        return "Tạo thành công với username: " + user.getUsername();
    }
}