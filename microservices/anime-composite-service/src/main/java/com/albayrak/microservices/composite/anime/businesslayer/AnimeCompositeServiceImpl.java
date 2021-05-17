package com.albayrak.microservices.composite.anime.businesslayer;

import com.albayrak.api.composite.anime.AnimeAggregate;
import com.albayrak.api.composite.anime.RecommendationSummary;
import com.albayrak.api.composite.anime.ReviewSummary;
import com.albayrak.api.composite.anime.ServiceAddress;
import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.review.Review;
import com.albayrak.microservices.composite.anime.integrationlayer.AnimeCompositeIntegration;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimeCompositeServiceImpl implements AnimeCompositeService{


    private static final Logger LOG = LoggerFactory.getLogger(AnimeCompositeServiceImpl.class);

    private final AnimeCompositeIntegration integration;

    private final ServiceUtils serviceUtils;


    public AnimeCompositeServiceImpl(AnimeCompositeIntegration integration, ServiceUtils serviceUtils) {
        this.integration = integration;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public AnimeAggregate getAnime(int animeId) {
        Anime anime = integration.getAnime(animeId);

        if(anime == null) throw new NotFoundException("No anime found for animeId: " + animeId);
        List<Recommendation> recommendations = integration.getRecommendations(animeId);

        List<Review> reviews= integration.getReviews(animeId);

        return createAnimeAggregate(anime, recommendations, reviews, serviceUtils.getServiceAddress());
    }

    @Override
    public void createAnime(AnimeAggregate model) {
        try {
            LOG.debug("createCompositeAnime: creates a new composite entity for animeId: {}", model.getAnimeId());
            Anime anime = new Anime(model.getAnimeId(), model.getTitle(), model.getAuthor(), null);
            integration.createAnime(anime);
            if (model.getRecommendations() != null) {
                model.getRecommendations().forEach(r -> {
                    Recommendation recommendation = new Recommendation(model.getAnimeId(), r.getRecommendationId(),
                            r.getAuthor(), r.getRate(), r.getContent(), null);
                    integration.createRecommendation(recommendation);
                });
            }
            if (model.getReviews() != null) {
                model.getReviews().forEach(r ->{
                    Review review = new Review(model.getAnimeId(), r.getReviewId(), r.getAuthor(),
                            r.getSubject(), r.getContent(), null);
                    integration.createReview(review);
                });
            }
            LOG.debug("createCompositeAnime: composite entities created for animeId: {}", model.getAnimeId());
        } catch (RuntimeException ex) {
            LOG.warn("createCompositeAnime failed", ex);
            throw ex;
        }
    }

    @Override
    public void deleteAnime(int animeId) {
        LOG.debug("deleteCompositeAnime: Deletes an anime aggregate for animeId: {}", animeId);
        integration.deleteAnime(animeId);
        integration.deleteRecommendations(animeId);
        integration.deleteReviews(animeId);
        LOG.debug("deleteCompositeAnime: aggregate entities deleted for animeId: {}", animeId);
    }


    private AnimeAggregate createAnimeAggregate(Anime anime, List<Recommendation> recommendations, List<Review> reviews, String serviceAddress) {
        //1. Setup anime information
        int animeId = anime.getAnimeId();
        String title = anime.getTitle();
        String author = anime.getAuthor();

        //2. Copy summary recommendation info, if any
        List<RecommendationSummary> recommendationSummaries = (recommendations == null) ? null :
                recommendations.stream()
                        .map(recommendation -> new RecommendationSummary(recommendation.getRecommendationId(), recommendation.getAuthor(), recommendation.getRate(), recommendation.getContent()))
                        .collect(Collectors.toList());

        //3. Copy summary reviews info, if any
        List<ReviewSummary> reviewSummaries = (reviews == null) ? null :
                reviews.stream().map(review -> new ReviewSummary(review.getReviewId(), review.getAuthor(), review.getSubject(), review.getContent()))
                        .collect(Collectors.toList());

        //4. Create info for microservice address
        String animeAddress = anime.getServiceAddress();
        String recommendationAddress = (recommendations != null && recommendations.size() > 0) ? recommendations.get(0).getServiceAddress() : "";
        String reviewsAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";

        ServiceAddress serviceAddresses = new ServiceAddress(serviceAddress, animeAddress, reviewsAddress, recommendationAddress);

        return new AnimeAggregate(animeId, title, author, recommendationSummaries, reviewSummaries, serviceAddresses);
    }
}
