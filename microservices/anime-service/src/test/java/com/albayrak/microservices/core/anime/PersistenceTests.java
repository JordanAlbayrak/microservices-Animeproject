package com.albayrak.microservices.core.anime;

import com.albayrak.microservices.core.anime.datalayer.AnimeEntity;
import com.albayrak.microservices.core.anime.datalayer.AnimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataMongoTest
public class PersistenceTests {

    @Autowired
    private AnimeRepository repository;
    private AnimeEntity savedEntity;
    int number = 1;
    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        AnimeEntity entity = new AnimeEntity(number+ 1, "ti", "au");
        savedEntity = repository.save(entity);

        //expected, actual
        //assertEqualsAnime(entity, savedEntity);

        //actual, expected
        assertThat(savedEntity, samePropertyValuesAs(entity));
    }

    @Test
    public void createAnimeEntity() {

        AnimeEntity newEntity = new AnimeEntity(number+2, "t", "b");
        repository.save(newEntity);

        AnimeEntity foundEntity = repository.findById(newEntity.getId()).get();
        //assertEqualsAnime(newEntity, foundEntity);

        assertThat(foundEntity, samePropertyValuesAs(newEntity));

        assertEquals(2, repository.count());
    }

    @Test
    public void updateAnimeEntity() {
        savedEntity.setTitle("t2");
        repository.save(savedEntity);

        AnimeEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("t2", foundEntity.getTitle());
    }

    @Test
    public void deleteAnimeEntity() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }


    @Test
    public void getAnimeEntity() {
        Optional<AnimeEntity> entity = repository.findByAnimeId(savedEntity.getAnimeId());

        assertTrue(entity.isPresent());
        //assertEqualsAnime(savedEntity, entity.get());

        assertThat(entity.get(), samePropertyValuesAs(savedEntity));

    }

    private void assertEqualsAnime(AnimeEntity expectedEntity, AnimeEntity actualEntity) {
        assertEquals(expectedEntity.getId(),               actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
        assertEquals(expectedEntity.getAnimeId(),        actualEntity.getAnimeId());
        assertEquals(expectedEntity.getTitle(),           actualEntity.getTitle());
        assertEquals(expectedEntity.getAuthor(),           actualEntity.getAuthor());
    }
}

