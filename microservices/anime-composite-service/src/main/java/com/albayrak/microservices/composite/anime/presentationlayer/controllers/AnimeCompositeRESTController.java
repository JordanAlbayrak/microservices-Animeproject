package com.albayrak.microservices.composite.anime.presentationlayer.controllers;

import com.albayrak.api.composite.anime.*;
import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.review.Review;
import com.albayrak.microservices.composite.anime.businesslayer.AnimeCompositeService;
import com.albayrak.microservices.composite.anime.integrationlayer.AnimeCompositeIntegration;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnimeCompositeRESTController implements AnimeCompositeServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(AnimeCompositeRESTController.class);

    private final AnimeCompositeService animeCompositeService;

    public AnimeCompositeRESTController(AnimeCompositeService animeCompositeService ) {
        this.animeCompositeService = animeCompositeService;
    }

    @Override
    public AnimeAggregate getCompositeAnime(int animeId) {
/*
        Anime anime = integration.getAnime(animeId);

        if(anime == null) throw new NotFoundException("No anime found for animeId: " + animeId);
        List<Recommendation> recommendations = integration.getRecommendations(animeId);

        List<Review> reviews= integration.getReviews(animeId);

        return createAnimeAggregate(anime, recommendations, reviews, serviceUtils.getServiceAddress());
*/
        LOG.debug("AnimeComposite received getAnimeComposite request.");

        AnimeAggregate animeAggregate = animeCompositeService.getAnime(animeId);

        return animeAggregate;
    }

    @Override
    public void createCompositeAnime(AnimeAggregate model) {

        LOG.debug("AnimeComposite received createAnimeComposite request.");

        animeCompositeService.createAnime(model);

    }

    @Override
    public void deleteCompositeAnime(int animeId) {

        LOG.debug("AnimeComposite received deleteAnimeComposite request.");

        animeCompositeService.deleteAnime(animeId);

    }

}
