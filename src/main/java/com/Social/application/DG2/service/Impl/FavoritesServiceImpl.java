package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.entity.Favorites;
import com.Social.application.DG2.service.FavoritesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class FavoritesServiceImpl implements FavoritesService {
    @Override
    public void saveFavorite(String userId, String articleId) {

    }

    @Override
    public void deleteFavorite(String userId, String articleId) {

    }

    @Override
    public Page<Favorites> getFavoritesByToken(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Favorites> getFavoritesByUserId(String userId, Pageable pageable) {
        return null;
    }
}
