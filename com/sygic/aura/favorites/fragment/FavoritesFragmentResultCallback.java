package com.sygic.aura.favorites.fragment;

import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.search.model.data.FavoritesItem;

public interface FavoritesFragmentResultCallback extends FragmentResultCallback {
    void onFavoritesFragmentResult(FavoritesItem favoritesItem);
}
