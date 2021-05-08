package com.albayrak.microservices.core.anime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class AnimeServiceApplicationTests {
	private static final int ANIME_ID_NO_CONTENT = 500;
	private static final int ANIME_ID_OKAY = 1;
	private static final int ANIME_ID_NOT_FOUND = 13;
	private static final int ANIME_ID_INVALID_NEGATIVE_VALUE = -1;

	@Autowired
	private WebTestClient client;

	@Test
	public void getAnimeById(){

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
	void contextLoads() {
	}

}