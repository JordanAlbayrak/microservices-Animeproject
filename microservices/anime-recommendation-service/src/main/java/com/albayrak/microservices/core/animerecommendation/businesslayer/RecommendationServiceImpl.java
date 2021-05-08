package com.albayrak.microservices.core.animerecommendation.businesslayer;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.microservices.core.animerecommendation.datalayer.RecommendationRepository;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public Recommendation getRecommendationById(int recommendationId) {

        ProductEntity entity = repository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));

        Product response = mapper.entityToModel(entity);
        response.setServiceAddress(serviceUtils.getServiceAddress());

        LOG.debug("Product getProductById: found productId: {}", response.getProductId());
        return response;
    }

    @Override
    public Recommendation createRecommendation(Recommendation model) {
        return null;
    }

    @Override
    public void deleteRecommendation(int recommendationId) {

    }
}
