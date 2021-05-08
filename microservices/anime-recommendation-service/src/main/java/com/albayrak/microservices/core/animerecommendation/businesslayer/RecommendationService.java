package com.albayrak.microservices.core.animerecommendation.businesslayer;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.recommendation.Recommendation;

public interface RecommendationService {

    public Recommendation getRecommendations(int animeId);

    public Recommendation createRecommendations(Recommendation model);

    public void deleteRecommendation(int animeId);

}
