package com.Social.application.DG2.service;

public interface FollowService {
    void followUser(String followingUserId);

    int getFollowingCount();

    int getFollowerCount();
    void unfollowUser(String followingUserId);

}
