package com.aci.acinews;

import android.content.Context;
import android.view.ViewGroup;

import com.aci.acinews.data.model.Article;
import com.aci.acinews.ui.adapters.NewsAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class NewsAdapterTest {

    private Context context;

    @Before
    public void setup() {
        context = mock(Context.class);
    }

    @Test
    public void testViewHolderBinding() {
        // Create a list of mock articles
        List<Article> mockArticles = new ArrayList<>();
        mockArticles.add(new Article("2024-01-10T22:41:25Z", "Why Crypto Idealogues Won’t Touch Bitcoin ETFs", "https://www.wired.com/story/bitcoin-etf-cryptocurrencies-split/", "https://media.wired.com/photos/65a0305c4aaf02fdf493f220/191:100/w_1280,c_limit/Not-Everyone-Is-Jazzed-About-Bitcoin-ETFs-Business-1299911534.jpg"));
        mockArticles.add(new Article("2024-01-10T22:41:25Z", "Why Crypto Idealogues Won’t Touch Bitcoin ETFs", "https://www.wired.com/story/bitcoin-etf-cryptocurrencies-split/", "https://media.wired.com/photos/65a0305c4aaf02fdf493f220/191:100/w_1280,c_limit/Not-Everyone-Is-Jazzed-About-Bitcoin-ETFs-Business-1299911534.jpg"));

        // Create a NewsAdapter instance
        NewsAdapter newsAdapter = new NewsAdapter(context);

        // Set the mock articles to the adapter
        newsAdapter.setArticles(mockArticles);

        //Create a mock ViewGroup
        ViewGroup mockParent = mock(ViewGroup.class);

        // Create a ViewHolder instance
        NewsAdapter.ViewHolder viewHolder = newsAdapter.onCreateViewHolder(mockParent, 0);

        // Bind the ViewHolder
        newsAdapter.onBindViewHolder(viewHolder, 0);

        // Verify that the ViewHolder's views are correctly bound
        assertEquals("Title 1", viewHolder.titleTextView.getText().toString());
        assertEquals("2024-03-15", viewHolder.dateTextView.getText().toString());

        // Verify that Glide is properly loading the image into the ImageView
        assertNotNull(viewHolder.imageNews.getDrawable());
        verify(Glide.with(context)).load("https://example.com/image1.jpg")
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(100, 100)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .dontAnimate()
                .into(viewHolder.imageNews);
    }
}
