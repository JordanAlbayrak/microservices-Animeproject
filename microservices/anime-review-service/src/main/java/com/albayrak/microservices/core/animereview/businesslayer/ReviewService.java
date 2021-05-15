package com.albayrak.microservices.core.animereview.businesslayer;

import com.albayrak.api.core.review.Review;

import java.util.List;

public interface ReviewService {

    public List<Review> getAnimeById(int animeId);

    public Review createReview(Review model);

    public void deleteReviews(int animeId);

}

