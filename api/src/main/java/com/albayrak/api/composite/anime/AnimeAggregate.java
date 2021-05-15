package com.albayrak.api.composite.anime;

import java.util.List;

public class AnimeAggregate {
    private int animeId;
    private String title;
    private String author;
    private List<RecommendationSummary> recommendations;
    private List<ReviewSummary> reviews;
    private ServiceAddress serviceAddress;

    public AnimeAggregate(int animeId, String title, String author, List<RecommendationSummary> recommendations, List<ReviewSummary> reviews, ServiceAddress serviceAddress) {
        this.animeId = animeId;
        this.title = title;
        this.author = author;
        this.recommendations = recommendations;
        this.reviews = reviews;
        this.serviceAddress = serviceAddress;
    }
    public AnimeAggregate(){
        this.animeId = 0;
        this.title = null;
        this.author = null;
        this.recommendations = null;
        this.reviews = null;
        this.serviceAddress = null;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRecommendations(List<RecommendationSummary> recommendations) {
        this.recommendations = recommendations;
    }

    public void setReviews(List<ReviewSummary> reviews) {
        this.reviews = reviews;
    }

    public void setServiceAddress(ServiceAddress serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public int getAnimeId() {
        return this.animeId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public List<RecommendationSummary> getRecommendations() {
        return this.recommendations;
    }

    public List<ReviewSummary> getReviews() {
        return this.reviews;
    }

    public ServiceAddress getServiceAddress() {
        return this.serviceAddress;
    }
}
