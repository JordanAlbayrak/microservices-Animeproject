package com.albayrak.api.composite.anime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AnimeCompositeServiceAPI {
    @GetMapping(
            value = "/anime-composite/{animeId}",
            produces = "application/json"
    )
    AnimeAggregate getAnime(@PathVariable int animeId);
}
