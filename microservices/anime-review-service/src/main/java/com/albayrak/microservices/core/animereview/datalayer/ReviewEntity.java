package com.albayrak.microservices.core.animereview.datalayer;

import javax.persistence.*;

@Entity
@Table(name = "reviews", indexes = { @Index(name = "reviews_unique_idx", unique = true, columnList = "animeId,reviewId") })
public class ReviewEntity {

    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private int animeId;

    private int reviewId;
    private String author;
    private String subject;
    private String content;

    public ReviewEntity() {
    }

    public ReviewEntity(int animeId, int reviewId, String author, String subject, String content) {
        this.animeId = animeId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public int getAnimeId() {
        return animeId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
