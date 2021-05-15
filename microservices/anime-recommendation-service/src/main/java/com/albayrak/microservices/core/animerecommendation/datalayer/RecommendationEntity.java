package com.albayrak.microservices.core.animerecommendation.datalayer;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "recommendations", indexes = { @Index(name = "recommendations_unique_idx", unique = true, columnList = "animeId,recommendationId") })
public class RecommendationEntity {


    @Id
    @GeneratedValue
    private int id;

    @Version
    private Integer version;

    private int animeId;


    private int recommendationId;

    private String author;
    private int rating;
    private String content;

    public RecommendationEntity() {
    }

    public RecommendationEntity(int animeId, int recommendationId, String author, int rating, String content) {
        this.animeId = animeId;
        this.recommendationId = recommendationId;
        this.author = author;
        this.rating = rating;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public int getAnimeId() {
        return animeId;
    }

    public int getRecommendationId() {
        return recommendationId;
    }

    public String getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }
}
