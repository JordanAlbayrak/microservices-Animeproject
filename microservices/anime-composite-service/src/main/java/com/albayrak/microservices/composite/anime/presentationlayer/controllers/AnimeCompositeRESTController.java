package com.albayrak.microservices.composite.anime.presentationlayer.controllers;

import com.albayrak.api.composite.anime.*;
import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.review.Review;
import com.albayrak.microservices.composite.anime.integrationlayer.AnimeCompositeIntegration;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.http.ServiceUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnimeCompositeRESTController implements AnimeCompositeServiceAPI {

    private final ServiceUtils serviceUtils;
    private AnimeCompositeIntegration integration;

    public AnimeCompositeRESTController(ServiceUtils serviceUtils, AnimeCompositeIntegration integration) {
        this.serviceUtils = serviceUtils;
        this.integration = integration;
    }

    @Override
    public AnimeAggregate getAnime(int animeId) {

        Anime anime = integration.getAnime(animeId);

        if(anime == null) throw new NotFoundException("No anime found for animeId: " + animeId);
        List<Recommendation> recommendations = integration.getRecommendations(animeId);

        List<Review> reviews= integration.getReviews(animeId);

        return createAnimeAggregate(anime, recommendations, reviews, serviceUtils.getServiceAddress());

    }

    private AnimeAggregate createAnimeAggregate(Anime anime, List<Recommendation> recommendations, List<Review> reviews, String serviceAddress) {

        //1. Setup anime information
        int animeId = anime.getAnimeId();
        String title = anime.getTitle();
        String author = anime.getAuthor();

        //2. Copy summary recommendation info, if any
        List<RecommendationSummary> recommendationSummaries = (recommendations == null) ? null :
                recommendations.stream()
                        .map(recommendation -> new RecommendationSummary(recommendation.getRecommendationId(), recommendation.getAuthor(), recommendation.getRate()))
                        .collect(Collectors.toList());

        //3. Copy summary reviews info, if any
        List<ReviewSummary> reviewSummaries = (reviews == null) ? null :
                reviews.stream().map(review -> new ReviewSummary(review.getReviewId(), review.getAuthor(), review.getSubject()))
                        .collect(Collectors.toList());

        //4. Create info for microservice address
        String animeAddress = anime.getServiceAddress();
        String recommendationAddress = (recommendations != null && recommendations.size() > 0) ? recommendations.get(0).getServiceAddress() : "";
        String reviewsAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";

        ServiceAddress serviceAddresses = new ServiceAddress(serviceAddress, animeAddress, reviewsAddress, recommendationAddress);

        return new AnimeAggregate(animeId, title, author, recommendationSummaries, reviewSummaries, serviceAddresses);


    }
}
