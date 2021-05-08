package com.albayrak.microservices.core.animereview.presentationlayer.controllers;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.review.Review;
import com.albayrak.api.core.review.ReviewServiceAPI;
import com.albayrak.utils.exceptions.InvalidInputException;
import com.albayrak.utils.exceptions.UnreleasedException;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewRESTController implements ReviewServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewRESTController.class);

    private final ServiceUtils serviceUtils;

    public ReviewRESTController(ServiceUtils serviceUtils) {
        this.serviceUtils = serviceUtils;
    }


    @Override
    public List<Review> getReviews(int animeId) {
        if(animeId < 1) throw new InvalidInputException("Invalid animeId: " + animeId);
        if(animeId == 213) {
            LOG.debug("No reviews found for animeId: {}", + animeId);
            return new ArrayList<>();
        }
        if(animeId == 502) throw new UnreleasedException("Unreleased animeId: " + animeId);
        List<Review> listReviews = new ArrayList<>();
        listReviews.add(new Review(animeId, 1, "Author 1", "Subject 1", "Content 1", serviceUtils.getServiceAddress()));
        listReviews.add(new Review(animeId, 2, "Author 2", "Subject 2", "Content 2", serviceUtils.getServiceAddress()));
        listReviews.add(new Review(animeId, 3, "Author 3", "Subject 3", "Content 3", serviceUtils.getServiceAddress()));

        LOG.debug("/Review found response size: {}", listReviews.size());

        return listReviews;
    }
}
