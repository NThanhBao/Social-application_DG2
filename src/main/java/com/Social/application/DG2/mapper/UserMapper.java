package com.Social.application.DG2.mapper;

import com.Social.application.DG2.dto.UsersDto;
import com.Social.application.DG2.entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UsersDto mapToDto(Users users) {
        return modelMapper.map(users, UsersDto.class);
    }
    public Users mapToEntity(UsersDto usersDto) {
        return modelMapper.map(usersDto, Users.class);
    }
}
