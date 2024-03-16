package com.aci.acinews.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aci.acinews.data.model.Article;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class NewsRepository {

    private Context context;

    @Inject
    public NewsRepository(Context context) {
        this.context = context;
    }

    public LiveData<List<Article>> getNews() {
        MutableLiveData<List<Article>> newsLiveData = new MutableLiveData<>();
        try {
            // Read JSON content from assets
            String json = loadJsonFromAsset("news.json");

            // Parse JSON into list of Article objects
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("articles");

            // Parse each article in the array
            List<Article> articles = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                Article article = new Gson().fromJson(element, Article.class);
                articles.add(article);
            }

            // Sort articles by published date
            Collections.sort(articles, new Comparator<Article>() {
                @Override
                public int compare(Article article1, Article article2) {
                    // Convert published dates to Date objects and compare them
                    Date date1 = parseDate(article1.getPublishedAt());
                    Date date2 = parseDate(article2.getPublishedAt());
                    return date2.compareTo(date1); // Descending order
                }
            });

            newsLiveData.setValue(articles);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle gracefully by returning empty list
            newsLiveData.setValue(new ArrayList<>());
        }
        return newsLiveData;
    }

    // Helper method to parse published date string to Date object
    private Date parseDate(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LiveData<List<Article>> searchNews(String query) {
        MutableLiveData<List<Article>> filteredNewsLiveData = new MutableLiveData<>();
        try {
            // Read JSON content from assets
            String json = loadJsonFromAsset("news.json");

            // Parse JSON into list of Article objects
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("articles");

            // Parse each article in the array and filter based on query
            List<Article> filteredArticles = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                Article article = new Gson().fromJson(element, Article.class);
                if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredArticles.add(article);
                }
            }

            // Sort filtered articles by published date
            Collections.sort(filteredArticles, new Comparator<Article>() {
                @Override
                public int compare(Article article1, Article article2) {
                    // Convert published dates to Date objects and compare them
                    Date date1 = parseDate(article1.getPublishedAt());
                    Date date2 = parseDate(article2.getPublishedAt());
                    return date2.compareTo(date1); // Descending order
                }
            });

            filteredNewsLiveData.setValue(filteredArticles);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle gracefully by returning empty list
            filteredNewsLiveData.setValue(new ArrayList<>());
        }
        return filteredNewsLiveData;
    }

    private String loadJsonFromAsset(String filename) throws IOException {
        InputStream inputStream = context.getAssets().open(filename);
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer, StandardCharsets.UTF_8);
    }
}




