package com.albayrak.microservices.composite.anime.businesslayer;

import com.albayrak.api.composite.anime.AnimeAggregate;

public interface AnimeCompositeService {

    public AnimeAggregate getAnime(int animeId);

    public void createAnime(AnimeAggregate model);

    public void deleteAnime(int animeId);
    
}
