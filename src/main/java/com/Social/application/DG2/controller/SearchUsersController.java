package com.Social.application.DG2.controller;

import com.Social.application.DG2.dto.SearchUserDto;
import com.Social.application.DG2.service.SearchUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class SearchUsersController {

    @Autowired
    private SearchUsersService searchUsersService;

    @GetMapping("/search")
    public List<SearchUserDto> searchUsersByFullName(@RequestParam String fullName,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int pageSize,
                                                     @RequestParam(defaultValue = "created_at") String sortName,
                                                     @RequestParam(defaultValue = "DESC") String sortType) {

        // Tạo một biến Sort.Direction để lưu hướng sắp xếp
        Sort.Direction direction;

        // Kiểm tra giá trị của sortType
        if (sortType.equalsIgnoreCase("ASC")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        // Tạo Pageable object để sử dụng cho việc sắp xếp
        Pageable sortedByName = PageRequest.of(page, pageSize, Sort.by(direction, sortName));

        // Sử dụng Pageable object để truy vấn dữ liệu từ service
        Page<SearchUserDto> usersPage = searchUsersService.findByFullNameContaining(fullName, sortedByName);

        // Trả về nội dung của trang
        return usersPage.getContent();
    }



}
