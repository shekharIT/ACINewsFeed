package com.aci.acinews.data.model;

import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;

    public Article(String publishedAt, String title, String url, String urlToImage) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
