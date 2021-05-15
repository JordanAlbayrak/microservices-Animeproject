package com.albayrak.microservices.core.animerecommendation.businesslayer;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.microservices.core.animerecommendation.datalayer.RecommendationEntity;
import com.albayrak.microservices.core.animerecommendation.datalayer.RecommendationRepository;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService{


    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepository repository;

    private final RecommendationMapper mapper;

    private final ServiceUtils serviceUtils;

    public RecommendationServiceImpl(RecommendationRepository repository, RecommendationMapper mapper, ServiceUtils serviceUtils) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public List<Recommendation> getAnimeById(int animeId) {
        List<RecommendationEntity> entityList = repository.findByAnimeId(animeId);
        List<Recommendation> list = mapper.entityListToModelList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtils.getServiceAddress()));

        LOG.debug("Recommendations getByAnimeId: response size: {}", list.size());
        LOG.debug("Recommendations getByAnimeId: entity {}", entityList.size());
        return list;
    }

    @Override
    public Recommendation createRecommendation(Recommendation model) {

        RecommendationEntity entity = mapper.modelToEntity(model);
        RecommendationEntity newEntity = repository.save(entity);

        LOG.debug("RecommendationService createRecommendation: create a recommendation entity: {}/{}", newEntity.getAnimeId(), newEntity.getRecommendationId());
        return mapper.entityToModel(newEntity);

    }

    @Override
    public void deleteRecommendations(int animeId) {

        LOG.debug("RecommendationService deleteRecommendations: tries to delete all recommendations for the anime with anime: {}", animeId);
        repository.deleteAll(repository.findByAnimeId(animeId));

    }

}