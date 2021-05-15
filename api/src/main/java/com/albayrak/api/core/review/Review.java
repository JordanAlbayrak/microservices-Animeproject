package com.albayrak.api.core.review;

public class Review {

    private int animeId;
    private int reviewId;
    private String author;
    private String subject;
    private String content;
    private String serviceAddress;

    public Review(int animeId, int reviewId, String author, String subject, String content, String serviceAddress) {
        this.animeId = animeId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public Review() {
        this.animeId = 0;
        this.reviewId = 0;
        this.author = null;
        this.subject = null;
        this.content = null;
        this.serviceAddress = null;
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

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public int getAnimeId() {
        return this.animeId;
    }

    public int getReviewId() {
        return this.reviewId;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getContent() {
        return this.content;
    }

    public String getServiceAddress() {
        return this.serviceAddress;
    }
}
