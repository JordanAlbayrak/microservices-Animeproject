package com.albayrak.microservices.core.anime;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.microservices.core.anime.datalayer.AnimeEntity;
import com.albayrak.microservices.core.anime.datalayer.AnimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.data.mongodb.port: 0"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class AnimeServiceApplicationTests {
	private static final int ANIME_ID_NO_CONTENT = 500;
	private static final int ANIME_ID_OKAY = 1;
	private static final int ANIME_ID_NOT_FOUND = 13;
	private static final int ANIME_ID_INVALID_NEGATIVE_VALUE = -1;

	@Autowired
	private WebTestClient client;

	@Autowired
	private AnimeRepository repository; //added persistence

	@BeforeEach
	public void setupDb(){ //added persistence
		repository.deleteAll();
	}

	@Test
	public void getAnimeById(){

		AnimeEntity entity = new AnimeEntity(ANIME_ID_OKAY, "Title-"+ANIME_ID_OKAY, "Author-"+ANIME_ID_OKAY);
		repository.save(entity);

		//make sure it's in the repo
		assertTrue(repository.findByAnimeId(ANIME_ID_OKAY).isPresent());
		

		client.get()
				.uri("/anime/" + ANIME_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.animeId").isEqualTo(ANIME_ID_OKAY);
	}

	@Test
	public void getAnimeNotFound(){
		client.get()
				.uri("/anime/" + ANIME_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/anime/" + ANIME_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("No anime found for animeId: " + ANIME_ID_NOT_FOUND);
	}
	@Test
	public void getAnimeInvalidParameterNegativeValue(){

		client.get()
				.uri("/anime/" + ANIME_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/anime/" + ANIME_ID_INVALID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("Invalid animeId: " + ANIME_ID_INVALID_NEGATIVE_VALUE);
	}
	@Test
	public void getAnimeNoContentFound() {

		client.get()
				.uri("/anime/" + ANIME_ID_NO_CONTENT)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/anime/" + ANIME_ID_NO_CONTENT)
				.jsonPath("$.message").isEqualTo("Unreleased animeId: " + ANIME_ID_NO_CONTENT);
	}

	@Test
	public void createAnime(){
		//create a anime model
		Anime model = new Anime(ANIME_ID_OKAY, "title-"+ANIME_ID_OKAY, "author","SA");

		//sent the post request
		client.post()
				.uri("/anime/")
				.body(just(model), Anime.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.animeId").isEqualTo(ANIME_ID_OKAY);

		assertTrue(repository.findByAnimeId(ANIME_ID_OKAY).isPresent());
	}

	@Test
	public void deleteAnime(){
		AnimeEntity entity = new AnimeEntity(ANIME_ID_OKAY, "title-"+ANIME_ID_OKAY, "author" );
		repository.save(entity);

		//make sure it's in the repo
		assertTrue(repository.findByAnimeId(ANIME_ID_OKAY).isPresent());

		client.delete()
				.uri("/anime/"+ANIME_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody();

		assertFalse(repository.findByAnimeId(ANIME_ID_OKAY).isPresent());

	}
	
	@Test
	void contextLoads() {
	}

}