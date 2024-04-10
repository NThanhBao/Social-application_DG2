package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.dto.UsersInfoDto;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.FollowService;
import com.Social.application.DG2.util.exception.InvalidRequestException;
import com.Social.application.DG2.util.exception.MethodNotAllowedException;
import com.Social.application.DG2.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public void followUser(String followingUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Users currentUser = usersRepository.findByUsername(currentUsername);
        Users followingUser = usersRepository.findById(followingUserId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng đang muốn theo dõi."));

        if (currentUser == null || followingUser == null) {
            throw new NotFoundException("Không tìm thấy người dùng hoặc người dùng đang muốn theo dõi.");
        }

        if (currentUser.getId().equals(followingUser.getId())) {
            throw new MethodNotAllowedException("Bạn không thể theo dõi chính mình.");
        }

        Set<Users> following = currentUser.getFollowingUser();

        boolean isAlreadyFollowing = following.contains(followingUser);

        if (!isAlreadyFollowing) {
            following.add(followingUser);
            usersRepository.save(currentUser);

            Set<Users> followingB = followingUser.getFollowingUser();
            boolean isAlreadyFollowedByTarget = followingB.contains(currentUser);

            if (!isAlreadyFollowedByTarget) {
                followingB.add(currentUser);
                usersRepository.save(followingUser);
            }
        } else {
            throw new InvalidRequestException("Bạn đã follow người dùng này trước đó rồi.");
        }
    }

    @Override
    public int getFollowingCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users users = usersRepository.findByUsername(currentUsername);
        return users.getFollowingUser().size();
    }

    @Override
    public int getFollowerCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        int followerCount = usersRepository.countByFollowingUsersUsername(currentUsername);
        return followerCount;
    }

    @Override
    public void unfollowUser(String followingUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Users currentUser = usersRepository.findByUsername(currentUsername);
        Users followingUser = usersRepository.findById(followingUserId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng đang muốn hủy theo dõi."));

        Set<Users> following = currentUser.getFollowingUser();
        if (!following.contains(followingUser)) {
            throw new NotFoundException("Bạn đã hủy theo dõi người này trước đó.");
        }

        following.remove(followingUser);
        usersRepository.save(currentUser);
    }
}
