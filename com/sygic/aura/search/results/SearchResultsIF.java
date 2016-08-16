package com.sygic.aura.search.results;

public interface SearchResultsIF {
    void onLoadingFinished(boolean z);

    void onSearchCanceled(boolean z);

    void onStartSearch();

    void onTickFinished(int i);
}
