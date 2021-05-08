package com.albayrak.microservices.composite.anime;

import com.albayrak.api.core.anime.Anime;
import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.api.core.review.Review;
import com.albayrak.microservices.composite.anime.integrationlayer.AnimeCompositeIntegration;
import com.albayrak.utils.exceptions.InvalidInputException;
import com.albayrak.utils.exceptions.NotFoundException;
import com.albayrak.utils.exceptions.UnreleasedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class AnimeCompositeServiceApplicationTests {

	private static final int ANIME_ID_NO_CONTENT = 500;
	private static final int ANIME_ID_OKAY = 1;
	private static final int ANIME_ID_NOT_FOUND = 213;

	private static final int ANIME_ID_INVALID_NEGATIVE_VALUE = -1;

	@Autowired
	private WebTestClient client;

	@MockBean
	private AnimeCompositeIntegration compositeIntegration;

	@BeforeEach
	void setup(){

		when(compositeIntegration.getAnime(ANIME_ID_OKAY))
				.thenReturn(new Anime(ANIME_ID_OKAY, "name 1", "author 1", "mock address"));

		//BDD equivalent
		given(compositeIntegration.getAnime(ANIME_ID_OKAY))
				.willReturn(new Anime(ANIME_ID_OKAY, "name 1", "author 1", "mock address"));

		when(compositeIntegration.getRecommendations(ANIME_ID_OKAY))
				.thenReturn(Collections.singletonList(new Recommendation(ANIME_ID_OKAY, 1, "author 1", 1, "conent 1", "mock address")));

		when(compositeIntegration.getReviews(ANIME_ID_OKAY))
				.thenReturn(Collections.singletonList(new Review(ANIME_ID_OKAY, 1, "author 1", "subject 1", "content 1", "mock address" )));

		when(compositeIntegration.getAnime(ANIME_ID_NOT_FOUND))
				.thenThrow(new NotFoundException("NOT FOUND: " + ANIME_ID_NOT_FOUND));

		when(compositeIntegration.getAnime(ANIME_ID_INVALID_NEGATIVE_VALUE))
				.thenThrow(new InvalidInputException("INVALID: " + ANIME_ID_INVALID_NEGATIVE_VALUE));

		when(compositeIntegration.getAnime(ANIME_ID_NO_CONTENT))
				.thenThrow(new UnreleasedException("UNRELEASED CONTENT: " + ANIME_ID_NO_CONTENT));

	}

	@Test
	public void getAnimeById(){

		int expectedLength = 1;

		client.get()
				.uri("/anime-composite/" + ANIME_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.animeId").isEqualTo(ANIME_ID_OKAY)
				.jsonPath("$.recommendations.length()").isEqualTo(expectedLength)
				.jsonPath("$.reviews.length()").isEqualTo(expectedLength);
	}

	@Test
	public void getAnimeNotFound(){

		client.get()
				.uri("/anime-composite/" + ANIME_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/anime-composite/" + ANIME_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: " + ANIME_ID_NOT_FOUND);
	}

	@Test
	public void getAnimeInvalidParameterNegativeValue(){

		client.get()
				.uri("/anime-composite/" + ANIME_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/anime-composite/" + ANIME_ID_INVALID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("INVALID: " + ANIME_ID_INVALID_NEGATIVE_VALUE);

	}

	@Test
	public void getAnimeNoContentFound() {

		client.get()
				.uri("/anime-composite/" + ANIME_ID_NO_CONTENT)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/anime-composite/" + ANIME_ID_NO_CONTENT)
				.jsonPath("$.message").isEqualTo("UNRELEASED CONTENT: " + ANIME_ID_NO_CONTENT);
	}

	@Test
	void contextLoads() {
	}

}
