package com.albayrak.api.core.anime;


public class Anime {
    private int animeId;
    private String title;
    private String author;
    private String serviceAddress;
    public Anime() {
        animeId = 0;
        title = null;
        author = null;
        serviceAddress = null;
    }
    public Anime(int animeId, String title, String author, String serviceAddress) {
        this.animeId = animeId;
        this.title = title;
        this.author = author;
        this.serviceAddress = serviceAddress;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public int getAnimeId() {
        return animeId;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getServiceAddress() {
        return serviceAddress;
    }
}
