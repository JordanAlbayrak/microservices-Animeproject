package com.albayrak.microservices.core.anime.businesslayer;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.microservices.core.anime.datalayer.AnimeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface AnimeMapper {

    @Mapping(target = "serviceAddress", ignore=true)
    Anime entityToModel(AnimeEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore=true),
            @Mapping(target = "version", ignore=true)

    })

    AnimeEntity modelToEntity(Anime model);

}
