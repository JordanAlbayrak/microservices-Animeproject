package com.albayrak.microservices.core.anime.businesslayer;

import com.albayrak.api.core.anime.Anime;

public interface AnimeService {

    public Anime getAnimeById(int animeId);

    public Anime createAnime(Anime model);

    public void deleteAnime(int animeId);

}
