package com.aci.acinews;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.aci.acinews.data.model.Article;
import com.aci.acinews.data.repository.NewsRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class NewsRepositoryTest {

    @Mock
    private Context context;

    private NewsRepository newsRepository;

    // Helper rule to ensure LiveData operations are performed synchronously
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        newsRepository = new NewsRepository(context);
    }

    @Test
    public void testGetNews() {
        // Mock the behavior of context.getAssets().open()
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test/news.json");
            when(context.getAssets().open("news.json")).thenReturn(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trigger the getNews() function
        LiveData<List<Article>> newsLiveData = newsRepository.getNews();

        // Create an observer to observe the LiveData
        Observer<List<Article>> observer = new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                // This method will be called when the LiveData emits a new value
                // You can perform assertions here
                assertEquals(3, articles.size());
            }
        };

        // Observe the LiveData with the created observer
        newsLiveData.observeForever(observer);
    }
}
