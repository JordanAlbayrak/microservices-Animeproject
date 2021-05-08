package com.albayrak.api.core.recommendation;

public class Recommendation {

    private int animeId;
    private int recommendationId;
    private String author;
    private int rate;
    private String content;
    private String serviceAddress;

    public Recommendation(int animeId, int recommendationId, String author, int rate, String content, String serviceAddress) {
        this.animeId = animeId;
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public Recommendation() {
        this.animeId = 0;
        this.recommendationId = 0;
        this.author = null;
        this.rate = 0;
        this.content = null;
        this.serviceAddress = null;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public int getAnimeId() {
        return this.animeId;
    }

    public int getRecommendationId() {
        return this.recommendationId;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getRate() {
        return this.rate;
    }

    public String getContent() {
        return this.content;
    }

    public String getServiceAddress() {
        return this.serviceAddress;
    }
}
