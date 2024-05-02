package com.Social.application.DG2.service;

import com.Social.application.DG2.entity.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoritesService {
    void saveFavorite(String userId, String articleId);
    void deleteFavorite(String userId, String articleId);
    Page<Favorites> getFavoritesByToken(Pageable pageable);
    Page<Favorites> getFavoritesByUserId(String userId, Pageable pageable);
}
