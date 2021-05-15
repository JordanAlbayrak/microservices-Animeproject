package com.albayrak.microservices.core.animereview.presentationlayer.controllers;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.review.Review;
import com.albayrak.api.core.review.ReviewServiceAPI;
import com.albayrak.microservices.core.animereview.businesslayer.ReviewService;
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

    private final ReviewService reviewService;

    public ReviewRESTController(ReviewService reviewService){
        this.reviewService = reviewService;

    }

    @Override
    public List<Review> getReviews(int animeId) {

        if(animeId < 1) throw new InvalidInputException("Invalid animeId: " + animeId);
        if(animeId == 502) throw new UnreleasedException("Unreleased animeId: " + animeId);

        List<Review> listReviews = reviewService.getAnimeById(animeId);

        LOG.debug("/getReviews found response size: {}", listReviews.size());

        return listReviews;
    }

    @Override
    public Review createReview(Review model) {
        Review review = reviewService.createReview(model);

        LOG.debug("REST Controller createReview: created an entity: {} / {}",review.getAnimeId(),review.getReviewId());

        return review;
    }

    @Override
    public void deleteReviews(int animeId) {

        LOG.debug("REST Controller deleteReview: trying to delete reviews for the anime with animeId: {}", animeId);
        reviewService.deleteReviews(animeId);

    }
}
