package com.albayrak.api.core.anime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AnimeServiceAPI {

    @GetMapping(
            value = "/anime/{animeId}",
            produces = "application/json"
    )
    Anime getAnime(@PathVariable int animeId);
}
