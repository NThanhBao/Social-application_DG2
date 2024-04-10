package com.Social.application.DG2.controller;

import com.Social.application.DG2.service.FollowService;
import com.Social.application.DG2.util.annotation.CheckLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class FollowController {

    @Autowired
    private FollowService followService;

    @CheckLogin
    @PostMapping("/follow/{followingUserId}")
    public ResponseEntity<String> followUser(@PathVariable String followingUserId) {
        followService.followUser(followingUserId);
        return new ResponseEntity<>("User followed successfully", HttpStatus.OK);
    }
    @CheckLogin
    @GetMapping("/following/count")
    public int getFollowingCount() {
        return followService.getFollowingCount();
    }
    @CheckLogin
    @GetMapping("/follower/count")
    public int getFollowerCount() {
        return followService.getFollowerCount();
    }

    @DeleteMapping("/auth/unfollow/{followingUserId}")
    public ResponseEntity<String> unfollowUser(@PathVariable String followingUserId) {
        followService.unfollowUser(followingUserId);
        return new ResponseEntity<>("User unfollowed successfully", HttpStatus.OK);
    }
}
