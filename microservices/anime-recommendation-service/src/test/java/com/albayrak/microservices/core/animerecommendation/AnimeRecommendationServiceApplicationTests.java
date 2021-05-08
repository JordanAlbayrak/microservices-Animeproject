package com.albayrak.microservices.core.animerecommendation;

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
class RecommendationServiceApplicationTests {
	private static final int ANIME_ID_NO_CONTENT = 501;
	private static final int ANIME_ID_OKAY = 1;
	private static final int ANIME_ID_NOT_FOUND = 113;
	private static final String ANIME_ID_INVALID_STRING = "not-integer";
	private static final int ANIME_ID_INVALID_NEGATIVE_VALUE = -1;

	@Autowired
	private WebTestClient client;

	@Test
	public void getRecommendationByAnimeId(){

		int expectedLength = 3;

		client.get()
				.uri("/recommendation?animeId=" + ANIME_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength)
				.jsonPath("$[0].animeId").isEqualTo(ANIME_ID_OKAY)
				.jsonPath("$[1].animeId").isEqualTo(ANIME_ID_OKAY)
				.jsonPath("$[2].animeId").isEqualTo(ANIME_ID_OKAY);
	}
	@Test
	public void getRecommendationMissingParameter(){

		client.get()
				.uri("/recommendation")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/recommendation")
				.jsonPath("$.message").isEqualTo("Required int parameter 'animeId' is not present");

	}

	@Test
	public void getRecommendationsInvalidParameterString(){

		client.get()
				.uri("/recommendation?animeId=" + ANIME_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/recommendation")
				.jsonPath("$.message").isEqualTo("Type mismatch.");

	}

	@Test
	public void getRecommendationsInvalidParameterNegativeValue(){

		client.get()
				.uri("/recommendation?animeId=" + ANIME_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/recommendation")
				.jsonPath("$.message").isEqualTo("Invalid animeId: " + ANIME_ID_INVALID_NEGATIVE_VALUE);
	}


	@Test
	public void getRecommendationsNotFound(){

		int expectedLength = 0;

		client.get()
				.uri("/recommendation?animeId=" + ANIME_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);

	}
	@Test
	public void getRecommendationNoContentFound() {

		client.get()
				.uri("/recommendation?animeId=" + ANIME_ID_NO_CONTENT)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/recommendation")
				.jsonPath("$.message").isEqualTo("Unreleased animeId: " + ANIME_ID_NO_CONTENT);
	}

	@Test
	void contextLoads() {
	}

}