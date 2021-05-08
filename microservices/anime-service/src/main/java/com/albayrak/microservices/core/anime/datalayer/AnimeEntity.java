package com.albayrak.microservices.core.anime.datalayer;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="anime")
public class AnimeEntity {

    @Id
    private String id;

    @Version
    private Integer version;

    @Indexed(unique=true)
    private int animeId; /* business key*/

    private String title;
    private String author;

    public AnimeEntity() {
    }

    public AnimeEntity(int animeId, String title, String author) {
        this.animeId = getAnimeId();
        this.title = title;
        this.author = author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
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
}
