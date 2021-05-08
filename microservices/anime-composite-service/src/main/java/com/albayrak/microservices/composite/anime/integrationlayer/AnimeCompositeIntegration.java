package com.albayrak.microservices.composite.anime.integrationlayer;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.anime.AnimeServiceAPI;
import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.recommendation.RecommendationServiceAPI;
import com.albayrak.api.core.review.Review;
import com.albayrak.api.core.review.ReviewServiceAPI;
import com.albayrak.utils.exceptions.InvalidInputException;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.http.HttpErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeCompositeIntegration implements AnimeServiceAPI, RecommendationServiceAPI, ReviewServiceAPI {


    private static final Logger LOG = LoggerFactory.getLogger(AnimeCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String animeServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;


    public AnimeCompositeIntegration(

            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.anime-service.host}") String animeServiceHost,
            @Value("${app.anime-service.port}") String animeServicePort,

            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") String recommendationServicePort,

            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") String reviewServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        animeServiceUrl = "http://" + animeServiceHost + ":" + animeServicePort + "/anime/";
        recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation?animeId=";
        reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review?animeId=";


    }

    @Override
    public Anime getAnime(int animeId) {
        try{
            String url = animeServiceUrl + animeId;
            LOG.debug("Will call getAnime API on URL: {}", url);

            Anime anime = restTemplate.getForObject(url, Anime.class);
            LOG.debug("Found a anime with id: {}", anime.getAnimeId());

            return anime;
        }
        catch (HttpClientErrorException ex){ //since we are the API client, we need to handle HTTP errors
            switch (ex.getStatusCode()){
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));

                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(getErrorMessage(ex));

                default:
                    LOG.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusText());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {

        try{
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();

        }catch(IOException ioex){
            return ioex.getMessage();

        }
    }

    @Override
    public List<Recommendation> getRecommendations(int animeId) {
        try{
            String url = recommendationServiceUrl + animeId;

            LOG.debug("Will call getRecommendations API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Recommendation>>() {
                    }).getBody();
            LOG.debug("Found {} recommendations for a anime with id: {}", recommendations.size(), animeId);
            return recommendations;
        }catch (Exception ex){
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Review> getReviews(int animeId) {

        try{
            String url = reviewServiceUrl + animeId;


            LOG.debug("Will call getReview API on URL: {}", url);
            List<Review> reviews = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {
                    }).getBody();

            LOG.debug("Found {} reviews for a anime with id: {}", reviews.size(), animeId);
            return reviews;
        }catch(Exception ex){
            LOG.warn("Got an exception while requesting reviews, will return zero reviews: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }}
