package com.albayrak.microservices.core.animerecommendation.datalayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity, Integer> {

    @Transactional(readOnly = true)
    List<RecommendationEntity> findByAnimeId(int animeId);

}
