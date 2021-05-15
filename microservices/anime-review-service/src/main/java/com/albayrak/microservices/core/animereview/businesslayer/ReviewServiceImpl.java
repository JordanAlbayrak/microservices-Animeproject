package com.albayrak.microservices.core.animereview.businesslayer;

import com.albayrak.api.core.review.Review;
import com.albayrak.microservices.core.animereview.datalayer.ReviewEntity;
import com.albayrak.microservices.core.animereview.datalayer.ReviewRepository;
import com.albayrak.utils.http.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository repository;

    private final ReviewMapper mapper;

    private final ServiceUtils serviceUtils;

    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper, ServiceUtils serviceUtils) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public List<Review> getAnimeById(int animeId) {
        List<ReviewEntity> entityList = repository.findByAnimeId(animeId);
        List<Review> list = mapper.entityListToModelList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtils.getServiceAddress()));

        LOG.debug("Review getByAnimeId: response size: {}", list.size());
        return list;
    }

    @Override
    public Review createReview(Review model) {
        ReviewEntity entity = mapper.modelToEntity(model);
        ReviewEntity newEntity = repository.save(entity);

        LOG.debug("ReviewService createReview: create a Review entity: {}/{}", model.getAnimeId(), model.getReviewId());
        return mapper.entityToModel(newEntity);

    }
    @Override
    public void deleteReviews(int animeId) {

        LOG.debug("deleteReview: delete Review for animeId: {} ", animeId);
        repository.deleteAll(repository.findByAnimeId(animeId));
    }
}
