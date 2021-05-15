package com.albayrak.api.core.review;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ReviewServiceAPI {

    @GetMapping(
            value = "/review",
            produces = "application/json"
    )
    List<Review> getReviews(@RequestParam(value = "animeId", required = true) int animeId);

    @PostMapping(
            value= "/review",
            consumes = "application/json",
            produces = "application/json"
    )
    Review createReview(@RequestBody Review model);


    @DeleteMapping(value="/review")
    void deleteReviews(@RequestParam(value = "animeId", required = true) int animeId);
}