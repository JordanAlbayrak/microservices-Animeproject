package com.albayrak.api.core.anime;

import org.springframework.web.bind.annotation.*;

public interface AnimeServiceAPI {

    @GetMapping(
            value = "/anime/{animeId}",
            produces = "application/json"
    )
    Anime getAnime(@PathVariable int animeId);

    @PostMapping(
            value= "/anime",
            consumes = "application/json",
            produces = "application/json"
    )
    Anime createAnime(@RequestBody Anime model);

    @DeleteMapping(
            value="/anime/{animeId}"
    )
    void deleteAnime(@PathVariable int animeId);
}