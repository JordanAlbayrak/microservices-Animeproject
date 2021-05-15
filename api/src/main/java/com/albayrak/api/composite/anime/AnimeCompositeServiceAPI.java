package com.albayrak.api.composite.anime;

import org.springframework.web.bind.annotation.*;

public interface AnimeCompositeServiceAPI {
    @GetMapping(
            value = "/anime-composite/{animeId}",
            produces = "application/json"
    )
    AnimeAggregate getCompositeAnime(@PathVariable int animeId);

    @PostMapping(
            value = "/anime-composite",
            consumes = "application/json"
    )
    void createCompositeAnime(@RequestBody AnimeAggregate model);

    @DeleteMapping(value = "/anime-composite/{animeId}")
    void deleteCompositeAnime(@PathVariable int animeId);

}
