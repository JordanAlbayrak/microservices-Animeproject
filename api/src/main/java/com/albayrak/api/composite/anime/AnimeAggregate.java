package com.albayrak.api.composite.anime;

import java.util.List;

public class AnimeAggregate {
    private final int animeId;
    private final String title;
    private final String author;
    private final List<RecommendationSummary> recommendations;
    private final List<ReviewSummary> reviews;
    private final ServiceAddress serviceAddress;

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
