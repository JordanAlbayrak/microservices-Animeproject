package com.albayrak.api.composite.anime;

public class ReviewSummary {

    private int reviewId;
    private String author;
    private String subject;
    private String content;

    public ReviewSummary(int reviewId, String author, String subject, String content) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
    public ReviewSummary(){
        this.reviewId = 0;
        this.author = null;
        this.subject = null;
        this.content = null;
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

    public int getReviewId() {
        return this.reviewId;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getContent() { return content; }
}
