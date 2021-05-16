package com.albayrak.microservices.core.anime.presentationlayer.controllers;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.anime.AnimeServiceAPI;
import com.albayrak.microservices.core.anime.businesslayer.AnimeService;
import com.albayrak.utils.exceptions.InvalidInputException;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.exceptions.UnreleasedException;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimeRESTController implements AnimeServiceAPI {



    private static final Logger LOG = LoggerFactory.getLogger(AnimeRESTController.class);


    private final AnimeService animeService;

    public AnimeRESTController(AnimeService animeService){
        this.animeService = animeService;

    }


    @Override
    public Anime getAnime(int animeId) {
        LOG.debug("/anime MS return the found anime for animeId: " + animeId);

        if(animeId < 1) throw new InvalidInputException("Invalid animeId: " + animeId);
        if(animeId == 500) throw new UnreleasedException("Unreleased animeId: " + animeId);

        //if (animeId == 13) throw new NotFoundException("No anime found for animeId: " + animeId);

        Anime anime = animeService.getAnimeById(animeId);

        //return new Anime(animeId, "title-" + animeId, 123, serviceUtils.getServiceAddress());
        return  anime;
    }
    @Override
    public Anime createAnime(Anime model) {
        Anime anime = animeService.createAnime(model);

        LOG.debug("REST createAnime: anime created for animeId: {}", anime.getAnimeId());

        return anime;
    }

    @Override
    public void deleteAnime(int animeId) {

        animeService.deleteAnime(animeId);

        LOG.debug("REST deleteAnime: tried to delete animeId: {}", animeId);

    }
}
