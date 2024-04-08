package com.Social.application.DG2.service;

import com.Social.application.DG2.dto.SearchUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchUsersService {
    Page<SearchUserDto> findByFullNameContaining(String fullName, Pageable pageable);


}
