package com.albayrak.microservices.core.animereview;

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
class ReviewServiceApplicationTests {


	private static final int ANIME_ID_NO_CONTENT = 502;
	private static final int ANIME_ID_OKAY = 1;
	private static final int ANIME_ID_NOT_FOUND = 213;
	private static final String ANIME_ID_INVALID_STRING = "not-integer";
	private static final int ANIME_ID_INVALID_NEGATIVE_VALUE = -1;

	@Autowired
	private WebTestClient client;

	@Test
	public void getReviewByAnimeId(){

		int expectedLength = 3;

		client.get()
				.uri("/review?animeId=" + ANIME_ID_OKAY)
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
	public void getReviewMissingParameter(){

		client.get()
				.uri("/review")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Required int parameter 'animeId' is not present");

	}

	@Test
	public void getReviewsInvalidParameterString(){

		client.get()
				.uri("/review?animeId=" + ANIME_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Type mismatch.");

	}

	@Test
	public void getReviewsInvalidParameterNegativeValue(){

		client.get()
				.uri("/review?animeId=" + ANIME_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Invalid animeId: " + ANIME_ID_INVALID_NEGATIVE_VALUE);
	}


	@Test
	public void getReviewsNotFound(){

		int expectedLength = 0;

		client.get()
				.uri("/review?animeId=" + ANIME_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);

	}
	@Test
	public void getReviewsNoContentFound() {

		client.get()
				.uri("/review?animeId=" + ANIME_ID_NO_CONTENT)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Unreleased animeId: " + ANIME_ID_NO_CONTENT);
	}
	@Test
	void contextLoads() {
	}

}
