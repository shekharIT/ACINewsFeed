package com.aci.acinews;

import static junit.framework.TestCase.assertEquals;

import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aci.acinews.data.model.Article;
import com.aci.acinews.ui.news.NewsFragment;
import com.aci.acinews.ui.viewmodels.NewsViewModel;

@RunWith(RobolectricTestRunner.class)
public class NewsFragmentTest {

    private NewsViewModel newsViewModel;
    private FragmentScenario<NewsFragment> fragmentScenario;

    @Before
    public void setup() {
        newsViewModel = mock(NewsViewModel.class);
        fragmentScenario = FragmentScenario.launchInContainer(NewsFragment.class);
        fragmentScenario.onFragment(fragment -> fragment.newsViewModel = newsViewModel);
    }

    @Test
    public void testFragmentCreation() {
        fragmentScenario.onFragment(fragment -> {
            assertNotNull(fragment);
        });
    }

    @Test
    public void testSearchNews() {
        // Mock the behavior of searchNews in NewsViewModel
        MutableLiveData<List<Article>> mockLiveData = new MutableLiveData<>();
        when(newsViewModel.getNews()).thenReturn(mockLiveData);

        // Launch the fragment
        fragmentScenario.onFragment(fragment -> {
            EditText searchEditText = fragment.getView().findViewById(R.id.search_edit_text);
            searchEditText.setText("search query");

            // Verify that searchNews is called with the correct query
            verify(newsViewModel).searchNews("search query");
        });
    }

    @Test
    public void testObserveViewModel() {
        // Prepare some mock data for the RecyclerView
        List<Article> mockArticles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article("2024-01-10T22:41:25Z", "Why Crypto Idealogues Won’t Touch Bitcoin ETFs", "https://www.wired.com/story/bitcoin-etf-cryptocurrencies-split/", "https://media.wired.com/photos/65a0305c4aaf02fdf493f220/191:100/w_1280,c_limit/Not-Everyone-Is-Jazzed-About-Bitcoin-ETFs-Business-1299911534.jpg");

            article.setTitle("Title " + i);
            mockArticles.add(article);
        }

        // Mock the behavior of getNews in NewsViewModel
        MutableLiveData<List<Article>> mockLiveData = new MutableLiveData<>();
        mockLiveData.setValue(mockArticles);
        when(newsViewModel.getNews()).thenReturn(mockLiveData);

        // Launch the fragment
        fragmentScenario.onFragment(fragment -> {
            // Check if the RecyclerView is updated with the mock data
            RecyclerView recyclerView = fragment.getView().findViewById(R.id.recycler_view);
            assertNotNull(recyclerView.getAdapter());
            assertNotNull(recyclerView.getAdapter().getItemCount());
            assert (recyclerView.getAdapter().getItemCount() == mockArticles.size());
        });
    }

    @Test
    public void testObserveViewModelError() {
        // Create a mock ViewModel
        NewsViewModel mockViewModel = mock(NewsViewModel.class);
        MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>(null);
        when(mockViewModel.getNews()).thenReturn(articlesLiveData);

        // Create the fragment with the mock ViewModel
        FragmentScenario<NewsFragment> fragmentScenario = FragmentScenario.launchInContainer(NewsFragment.class);
        fragmentScenario.onFragment(fragment -> {
            fragment.newsViewModel = mockViewModel; // Inject the mock ViewModel
            fragment.observeViewModel(); // Manually call observeViewModel() to observe LiveData

            // Simulate a change in the LiveData
            articlesLiveData.postValue(null);

            // Capture the toast message
            Toast toast = ShadowToast.getLatestToast();
            assertNotNull(toast);

            // Verify the toast message content
            assertEquals("Failed to fetch articles", ShadowToast.getTextOfLatestToast());
        });
    }
}