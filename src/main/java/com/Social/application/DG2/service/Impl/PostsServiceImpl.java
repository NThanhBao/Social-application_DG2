package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.dto.PostsDto;
import com.Social.application.DG2.entity.Medias;
import com.Social.application.DG2.entity.Posts;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.MediaRepository;
import com.Social.application.DG2.repositories.PostsRepository;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.PostsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostsServiceImpl implements PostsService{
    @Autowired
    private  PostsRepository postsRepository;
    @Autowired
    private  UsersRepository usersRepository;
    @Autowired
    private  MediaRepository mediaRepository;

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


        List<String> mediasIdStrings = postDto.getMediasId();
        List<Medias> medias = new ArrayList<>();
        for (String mediaId : mediasIdStrings) {
            Medias media = mediaRepository.findById(mediaId).orElse(null);
            if (media != null) {
                medias.add(media);
            }
        }
        post.setMedias(medias);

        post.setUserId(currentUser);
        post.setCreateAt(new Timestamp(System.currentTimeMillis()));
        return postsRepository.save(post);
    }

    public void updatePost(UUID postId, PostsDto updatedPost) {
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

        post.setTitle(updatedPost.getTitle());
        post.setBody(updatedPost.getBody());
        post.setStatus(updatedPost.getStatus());

        List<String> mediasIdStrings = updatedPost.getMediasId();
        List<Medias> medias = new ArrayList<>();
        for (String mediaId : mediasIdStrings) {
            Medias media = mediaRepository.findById(mediaId).orElse(null);
            if (media != null) {
                medias.add(media);
            }
        }
        post.setMedias(medias);

        post.setCreateAt(new Timestamp(System.currentTimeMillis()));

        postsRepository.save(post);
    }

    @Override
    public void deletePost(UUID postId) {
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
            throw new AccessDeniedException("Bạn không có quyền Xóa bài đăng này");
        }

        postsRepository.deleteById(post.toString());
    }

    @Override
    public Page<Posts> getPostsByUserId(Pageable pageable, UUID userId) {
        Optional<Users> userOptional = usersRepository.findById(String.valueOf(userId));
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return postsRepository.findByUserId(user, pageable);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public int getNumberOfPostsByUserId(UUID userId) {
        Optional<Users> userOptional = usersRepository.findById(String.valueOf(userId));
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return postsRepository.countByUserId(user);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public List<Posts> getAllPosts(Pageable pageable) {
        return postsRepository.findAll();
    }

    @Override
    public int getNumberOfPosts() {
        List<Posts> allPosts = postsRepository.findAll();
        return allPosts.size();
    }


    @Override
    public Page<Posts> getListOfPostsByLoggedInUser(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users users = usersRepository.findByUsername(currentUsername);
        if (users != null) {
            return postsRepository.findByUserId(users, pageable);
        } else {
            throw new EntityNotFoundException("User not found with username: " + currentUsername);
        }
    }

    @Override
    public int getNumberOfPostsByLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users users = usersRepository.findByUsername(currentUsername);
        if (users != null) {
            return postsRepository.countByUserId(users);
        } else {
            throw new EntityNotFoundException("User not found with username: " + currentUsername);
        }
    }
}