package com.albayrak.microservices.core.animerecommendation.presentationlayer.controllers;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.recommendation.RecommendationServiceAPI;
import com.albayrak.microservices.core.animerecommendation.businesslayer.RecommendationService;
import com.albayrak.utils.exceptions.InvalidInputException;
import com.albayrak.utils.exceptions.UnreleasedException;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationRESTController implements RecommendationServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationRESTController.class);

    private final RecommendationService recommendationService;

    public RecommendationRESTController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;

    }


    @Override
    public List<Recommendation> getRecommendations(int animeId) {

        if(animeId < 1) throw new InvalidInputException("Invalid animeId: " + animeId);
        if(animeId == 501) throw new UnreleasedException("Unreleased animeId: " + animeId);


        List<Recommendation> listRecommendations = recommendationService.getAnimeById(animeId);

        LOG.debug("/getRecommendations found response size: {}", listRecommendations.size());

        return listRecommendations;
    }
    @Override
    public Recommendation createRecommendation(Recommendation model) {
        Recommendation recommendation = recommendationService.createRecommendation(model);

        LOG.debug("REST Controller createRecommendation: created an entity: {} / {}",recommendation.getAnimeId(),recommendation.getRecommendationId());

        return recommendation;
    }

    @Override
    public void deleteRecommendations(int animeId) {

        LOG.debug("REST Controller deleteRecommendation: trying to delete recommendations for the anime with animeId: {}", animeId);
        recommendationService.deleteRecommendations(animeId);

    }
}

