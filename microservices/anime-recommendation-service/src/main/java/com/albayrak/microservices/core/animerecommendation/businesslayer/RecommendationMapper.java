package com.albayrak.microservices.core.animerecommendation.businesslayer;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.microservices.core.animerecommendation.datalayer.RecommendationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel="spring")
public interface RecommendationMapper {

    @Mappings({
            @Mapping(target = "rate", source = "entity.rating"),
            @Mapping(target = "serviceAddress", ignore = true)

    })
    Recommendation entityToModel(RecommendationEntity entity);

    @Mappings({
            @Mapping(target = "rating", source="model.rate"),
            @Mapping(target = "id", ignore=true),
            @Mapping(target = "version", ignore=true)

    })

    RecommendationEntity modelToEntity(Recommendation model);

    List<Recommendation> entityListToModelList(List<RecommendationEntity> entity);
    List<Recommendation> modelListToEntityList(List<RecommendationEntity> model);


}


