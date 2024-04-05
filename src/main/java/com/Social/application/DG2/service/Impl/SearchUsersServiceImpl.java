package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.dto.SearchUserDto;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.SearchRepository;
import com.Social.application.DG2.service.SearchUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchUsersServiceImpl implements SearchUsersService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public Page<SearchUserDto> findByFullNameContaining(String fullName, Pageable pageable) {
        Page<Users> usersPage = searchRepository.findByFullNameAndNotAdminRole(fullName, pageable);
        return usersPage.map(this::convertToDTO);
    }


    private SearchUserDto convertToDTO(Users user) {
        SearchUserDto dto = new SearchUserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMail(user.getMail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.isGender());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAddress(user.getAddress());
        dto.setAvatar(user.getAvatar());
        dto.setCreateAt(user.getCreateAt());
        return dto;
    }
}