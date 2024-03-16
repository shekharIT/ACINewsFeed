package com.aci.acinews;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.aci.acinews.data.model.Article;
import com.aci.acinews.data.repository.NewsRepository;
import com.aci.acinews.ui.viewmodels.NewsViewModel;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NewsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private NewsRepository newsRepository;

    private NewsViewModel newsViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        newsViewModel = new NewsViewModel(newsRepository);
    }

    @Test
    public void testFetchNews() {
        // Mock the list of articles to be returned by the repository
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("2024-01-10T22:41:25Z", "Why Crypto Idealogues Won’t Touch Bitcoin ETFs", "https://www.wired.com/story/bitcoin-etf-cryptocurrencies-split/", "https://media.wired.com/photos/65a0305c4aaf02fdf493f220/191:100/w_1280,c_limit/Not-Everyone-Is-Jazzed-About-Bitcoin-ETFs-Business-1299911534.jpg"));
        articles.add(new Article("2024-01-11T18:18:13Z", "Why Crypto Idealogues Won’t Touch Bitcoin ETFs", "https://www.wired.com/story/bitcoin-etf-cryptocurrencies-split/", "https://media.wired.com/photos/65a0305c4aaf02fdf493f220/191:100/w_1280,c_limit/Not-Everyone-Is-Jazzed-About-Bitcoin-ETFs-Business-1299911534.jpg"));
        LiveData<List<Article>> newsLiveData = new MutableLiveData<>(articles);

        // Mock the behavior of the getNews() method in the repository
        when(newsRepository.getNews()).thenReturn(newsLiveData);

        // Call the fetchNews() method in the ViewModel
        newsViewModel.fetchNews();

        // Get the value of the LiveData from the ViewModel
        LiveData<List<Article>> newsLiveDataFromViewModel = newsViewModel.getNews();

        // Verify that the LiveData from the ViewModel contains the expected list of articles
        assertNotNull(newsLiveDataFromViewModel.getValue());
        assertEquals(articles.size(), newsLiveDataFromViewModel.getValue().size());
        assertEquals(articles, newsLiveDataFromViewModel.getValue());
    }

    @Test
    public void testSearchNews() {
        // Mock the list of filtered articles to be returned by the repository
        List<Article> filteredArticles = new ArrayList<>();
        filteredArticles.add(new Article("2024-01-10T22:41:25Z", "Why Crypto Idealogues Won’t Touch Bitcoin ETFs", "https://www.wired.com/story/bitcoin-etf-cryptocurrencies-split/", "https://media.wired.com/photos/65a0305c4aaf02fdf493f220/191:100/w_1280,c_limit/Not-Everyone-Is-Jazzed-About-Bitcoin-ETFs-Business-1299911534.jpg"));
        LiveData<List<Article>> filteredNewsLiveData = new MutableLiveData<>(filteredArticles);

        // Mock the behavior of the searchNews() method in the repository
        when(newsRepository.searchNews("Test")).thenReturn(filteredNewsLiveData);

        // Call the searchNews() method on the ViewModel with the search query "Test"
        newsViewModel.searchNews("Test");

        // Get the value of the LiveData from the ViewModel
        LiveData<List<Article>> newsLiveDataFromViewModel = newsViewModel.getNews();

        // Verify that the LiveData from the ViewModel contains the expected list of filtered articles
        assertNotNull(newsLiveDataFromViewModel.getValue());
        assertEquals(filteredArticles.size(), newsLiveDataFromViewModel.getValue().size());
        assertEquals(filteredArticles, newsLiveDataFromViewModel.getValue());
    }
}