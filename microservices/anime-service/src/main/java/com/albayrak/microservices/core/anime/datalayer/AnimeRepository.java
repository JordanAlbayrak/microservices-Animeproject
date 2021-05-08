package com.albayrak.microservices.core.anime.datalayer;


import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AnimeRepository extends PagingAndSortingRepository<AnimeEntity, String> {

    Optional<AnimeEntity> findByAnimeId(int animeId);
}
