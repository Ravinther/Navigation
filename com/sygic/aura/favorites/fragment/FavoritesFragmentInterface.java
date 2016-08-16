package com.sygic.aura.favorites.fragment;

import android.content.Context;
import com.sygic.aura.favorites.FavoritesAdapter;
import com.sygic.aura.favorites.FavoritesAdapter.Mode;
import com.sygic.aura.favorites.model.PagerFavoritesFragment;
import com.sygic.aura.search.model.data.FavoritesItem;

public interface FavoritesFragmentInterface {
    void finish(FavoritesItem favoritesItem);

    Context getContext();

    boolean onDeleteItem(FavoritesItem favoritesItem);

    void onFirstFragmentCreated(PagerFavoritesFragment pagerFavoritesFragment);

    void onFirstPageLoaded(FavoritesAdapter favoritesAdapter);

    void onRenameItem(long j, String str);

    void onReorderItem(long j, long j2);

    void requestRefresh(Mode mode);
}
