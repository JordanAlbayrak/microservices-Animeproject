package com.albayrak.api.core.recommendation;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RecommendationServiceAPI {

    @GetMapping(
            value = "/recommendation",
            produces = "application/json"
    )
    List<Recommendation> getRecommendations(@RequestParam(value = "animeId", required = true) int animeId);

    @PostMapping(
            value= "/recommendation",
            consumes = "application/json",
            produces = "application/json"
    )
    Recommendation createRecommendation(@RequestBody Recommendation model);


    @DeleteMapping(value="/recommendation")
    void deleteRecommendations(@RequestParam(value = "animeId", required = true) int animeId);
}
