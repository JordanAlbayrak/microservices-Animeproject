package com.albayrak.microservices.core.animerecommendation.datalayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
public interface RecommendationRepository extends CrudRepository<RecommendationEntity, String> {


    List<RecommendationEntity> findByAnimeId(int animeId);

}
