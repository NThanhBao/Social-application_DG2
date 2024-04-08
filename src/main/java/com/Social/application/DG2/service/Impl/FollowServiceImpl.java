package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.FollowService;
import com.Social.application.DG2.util.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public void followUser(String followingUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Users users = usersRepository.findByUsername(currentUsername);
        Optional<Users> followingUserOptional = usersRepository.findById(followingUserId);

        if (users != null && followingUserOptional.isPresent()) {
            Users followingUser = followingUserOptional.get();

            if (!currentUsername.equals(followingUser.getUsername())) {
                List<Users> following = users.getFollowingUser();

                boolean isAlreadyFollowing = following.stream()
                        .anyMatch(user -> user.getId().equals(followingUser.getId()));
                if (!isAlreadyFollowing) {
                    following.add(followingUser);
                    users.setFollowingUser(following);
                    usersRepository.save(users);
                } else {
                    throw new UnauthorizedException("Bạn đã theo dõi người này.");
                }
            } else {
                throw new UnauthorizedException("Bạn không thể theo dõi chính mình.");
            }
        } else {
            throw new UnauthorizedException("Không tìm thấy người dùng hoặc người dùng đang muốn theo dõi.");
        }
    }

}
