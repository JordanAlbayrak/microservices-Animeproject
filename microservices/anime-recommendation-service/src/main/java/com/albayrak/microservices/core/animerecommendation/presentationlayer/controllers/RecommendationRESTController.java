package com.albayrak.microservices.core.animerecommendation.presentationlayer.controllers;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.recommendation.RecommendationServiceAPI;
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

    private final ServiceUtils serviceUtils;

    public RecommendationRESTController(ServiceUtils serviceUtils) {
        this.serviceUtils = serviceUtils;
    }


    @Override
    public List<Recommendation> getRecommendations(int animeId) {

        if(animeId < 1) throw new InvalidInputException("Invalid animeId: " + animeId);
        if(animeId == 113) {
            LOG.debug("No recommendations found for animeId: {}", + animeId);
            return new ArrayList<>();
        }
        if(animeId == 501) throw new UnreleasedException("Unreleased animeId: " + animeId);
        List<Recommendation> listRecommendations = new ArrayList<>();
        listRecommendations.add(new Recommendation(animeId, 1, "Author 1", 1, "Content 1", serviceUtils.getServiceAddress()));
        listRecommendations.add(new Recommendation(animeId, 2, "Author 2", 2, "Content 2", serviceUtils.getServiceAddress()));
        listRecommendations.add(new Recommendation(animeId, 3, "Author 3", 3, "Content 3", serviceUtils.getServiceAddress()));

        LOG.debug("/recommendations found response size: {}", listRecommendations.size());

        return listRecommendations;
    }
}
