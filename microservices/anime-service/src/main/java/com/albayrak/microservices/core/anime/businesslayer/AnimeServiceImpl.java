package com.albayrak.microservices.core.anime.businesslayer;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.microservices.core.anime.datalayer.AnimeEntity;
import com.albayrak.microservices.core.anime.datalayer.AnimeRepository;
import com.albayrak.utils.exceptions.InvalidInputException;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.http.ServiceUtils;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnimeServiceImpl implements AnimeService{

    private static final Logger LOG = LoggerFactory.getLogger(AnimeServiceImpl.class);

    private final AnimeRepository repository;

    private final AnimeMapper mapper;

    private final ServiceUtils serviceUtils;

    public AnimeServiceImpl(AnimeRepository repository, AnimeMapper mapper, ServiceUtils serviceUtils) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public Anime getAnimeById(int animeId) {
        AnimeEntity entity = repository.findByAnimeId(animeId)
                .orElseThrow(() -> new NotFoundException("No anime found for animeId: " + animeId));

        Anime response = mapper.entityToModel(entity);
        response.setServiceAddress(serviceUtils.getServiceAddress());

        LOG.debug("Anime getAnimeById: found animeId: {}", response.getAnimeId());
        return response;
    }

    @Override
    public Anime createAnime(Anime model) {

        try{
            AnimeEntity entity = mapper.modelToEntity(model);
            AnimeEntity newEntity = repository.save(entity);

            LOG.debug("createAnime: entity created for animeId: {}", model.getAnimeId());
            return mapper.entityToModel(newEntity);
        }catch (DuplicateKeyException dke){
            throw new InvalidInputException("Duplicate key, animeId: " +model.getAnimeId());
        }
    }

    @Override
    public void deleteAnime(int animeId) {

        LOG.debug("deleteAnime: entity delete for animeId: {}", animeId);
        repository.findByAnimeId(animeId).ifPresent(e -> repository.delete(e));

    }
}
