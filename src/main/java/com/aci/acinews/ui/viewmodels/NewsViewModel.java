package com.aci.acinews.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aci.acinews.data.model.Article;
import com.aci.acinews.data.repository.NewsRepository;
import java.util.List;

import javax.inject.Inject;

public class NewsViewModel extends ViewModel {

    private NewsRepository newsRepository;
    private MutableLiveData<List<Article>> newsLiveData = new MutableLiveData<>();

    @Inject
    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        fetchNews(); // Fetch initial news data
    }

    public LiveData<List<Article>> getNews() {
        return newsLiveData;
    }

    public void setNewsData(List<Article> articles) {
        newsLiveData.setValue(articles);
    }

    public void fetchNews() {
        newsRepository.getNews().observeForever(articles -> newsLiveData.postValue(articles));
    }

    public void searchNews(String query) {
            newsRepository.searchNews(query).observeForever(filteredArticles -> newsLiveData.postValue(filteredArticles));
    }
}




