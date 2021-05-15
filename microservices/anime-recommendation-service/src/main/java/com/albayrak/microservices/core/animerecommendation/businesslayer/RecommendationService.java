package com.albayrak.microservices.core.animerecommendation.businesslayer;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.recommendation.Recommendation;

import java.util.List;

public interface RecommendationService {

    public List<Recommendation> getAnimeById(int animeId);

    public Recommendation createRecommendation(Recommendation model);

    public void deleteRecommendations(int animeId);

}
