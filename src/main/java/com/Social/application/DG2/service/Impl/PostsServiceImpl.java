package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.dto.PostsDto;
import com.Social.application.DG2.entity.Posts;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.mapper.PostsMapper;
import com.Social.application.DG2.repositories.PostsRepository;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.PostsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostsServiceImpl implements PostsService{
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    public PostsServiceImpl(PostsRepository postsRepository, UsersRepository usersRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Posts createPosts(PostsDto postDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);

        if (currentUser == null) {
            throw new EntityNotFoundException("Current user not found");
        }

        Posts post = new Posts();
        post.setId(UUID.randomUUID().toString());
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setStatus(postDto.getStatus());

        post.setTotalLike(0);
        post.setTotalComment(0);

        post.setUserId(currentUser);
        post.setCreateAt(new Timestamp(System.currentTimeMillis()));
        return postsRepository.save(post);
    }

    @Override
    public List<Posts> getPostsByUserId(UUID userId) {
        Optional<Users> userOptional = usersRepository.findById(String.valueOf(userId));
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return postsRepository.findByUserId(user);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public List<Posts> getAllPosts() {
        return postsRepository.findAll();
    }

    public void updatePost(UUID postId, Posts updatedPost) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);

        if (currentUser == null) {
            throw new EntityNotFoundException("Không tìm thấy người dùng hiện tại");
        }

        Optional<Posts> optionalPost = postsRepository.findById(postId.toString());
        if (!optionalPost.isPresent()) {
            throw new EntityNotFoundException("Không tìm thấy bài đăng có ID: " + postId);
        }

        Posts post = optionalPost.get();

        if (!post.getUserId().equals(currentUser)) {
            throw new AccessDeniedException("Bạn không có quyền cập nhật bài đăng này");
        }

        // Cập nhật thông tin của bài đăng
        post.setTitle(updatedPost.getTitle());
        post.setBody(updatedPost.getBody());
        post.setStatus(updatedPost.getStatus());
        // Cập nhật ngày chỉnh sửa
        post.setCreateAt(new Timestamp(System.currentTimeMillis()));

        postsRepository.save(post);
    }



    @Override
    public void deletePost(UUID postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);

        if (currentUser == null) {
            throw new EntityNotFoundException("Current user not found");
        }

        Optional<Posts> optionalPost = postsRepository.findById(postId.toString());
        if (optionalPost.isPresent()) {
            Posts post = optionalPost.get();

            if (!post.getUserId().equals(currentUser)) {
                throw new AccessDeniedException("You don't have permission to delete this post");
            }
            postsRepository.deleteById(postId.toString());
        } else {
            throw new EntityNotFoundException("Post not found with ID: " + postId);
        }
    }
}
