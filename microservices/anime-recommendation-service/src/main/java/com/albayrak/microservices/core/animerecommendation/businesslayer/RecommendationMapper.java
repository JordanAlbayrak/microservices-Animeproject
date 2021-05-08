package com.albayrak.microservices.core.animerecommendation.businesslayer;

import com.albayrak.api.core.recommendation.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface RecommendationMapper {

    @Mapping(target = "serviceAddress", ignore=true)
    Recommendation entityToModel(RecommendationEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore=true),
            @Mapping(target = "version", ignore=true)

    })

    RecommendationEntity modelToEntity(Recommendation model);

}

