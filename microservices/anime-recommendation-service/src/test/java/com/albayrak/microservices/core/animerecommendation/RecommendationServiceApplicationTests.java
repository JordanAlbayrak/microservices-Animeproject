package com.albayrak.microservices.core.animerecommendation;

import com.albayrak.api.core.recommendation.Recommendation;
import com.albayrak.microservices.core.animerecommendation.datalayer.RecommendationEntity;
import com.albayrak.microservices.core.animerecommendation.datalayer.RecommendationRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:main-db"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class RecommendationServiceApplicationTests {
	private static final int ANIME_ID_NO_CONTENT = 501;
	private static final int ANIME_ID_OKAY = 1;
	private static final int ANIME_ID_NOT_FOUND = 113;
	private static final String ANIME_ID_INVALID_STRING = "not-integer";
	private static final int ANIME_ID_INVALID_NEGATIVE_VALUE = -1;

	private static final int RECOMMENDATION_ID = 1;

	@Autowired
	private WebTestClient client;


	@Autowired
	private RecommendationRepository repository; //added persistence

	@BeforeEach
	public void setupDb(){ //added persistence
		repository.deleteAll();
	}


	@Test
	public void getRecommendationByAnimeId(){

		int expectedLength = 3;

		//add the recommendations to the repo
		RecommendationEntity entity1 = new RecommendationEntity(ANIME_ID_OKAY, RECOMMENDATION_ID, "author-1", 1, "content-1");
		repository.save(entity1);
		RecommendationEntity entity2 = new RecommendationEntity(ANIME_ID_OKAY, RECOMMENDATION_ID + 1, "author-2", 2, "content-2");
		repository.save(entity2);
		RecommendationEntity entity3 = new RecommendationEntity(ANIME_ID_OKAY, RECOMMENDATION_ID + 2, "author-3", 3, "content-3");
		repository.save(entity3);

		assertEquals(expectedLength, repository.findByAnimeId(ANIME_ID_OKAY).size());

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
	public void createRecommendation(){
		int expectedSize =1;
		//create the recommendaiton

		Recommendation recommendation = new Recommendation(ANIME_ID_OKAY, RECOMMENDATION_ID,
				"Author " + RECOMMENDATION_ID, RECOMMENDATION_ID, "Content " + RECOMMENDATION_ID, "SA");


		//send the POST request
		client.post()
				.uri("/recommendation/")
				.body(just(recommendation), Recommendation.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedSize, repository.findByAnimeId(ANIME_ID_OKAY).size());


	}

	@Test
	void deleteRecommendations(){

		//create a recommendation entity

		RecommendationEntity entity = new RecommendationEntity(ANIME_ID_OKAY, RECOMMENDATION_ID, "author-1", 1, "content-1");
		//save it
		repository.save(entity);

		//verify there are exactly 1  entity for anime id 1
		assertEquals(1, repository.findByAnimeId(ANIME_ID_OKAY).size());

		//send the DELETE request
		client.delete()
				.uri("/recommendation?animeId=" + ANIME_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody();

		//verify there are no entities for anime id 1
		assertEquals(0, repository.findByAnimeId(ANIME_ID_OKAY).size());
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