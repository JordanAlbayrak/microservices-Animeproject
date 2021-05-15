package com.albayrak.microservices.core.animereview;

import com.albayrak.api.core.review.Review;
import com.albayrak.microservices.core.animereview.datalayer.ReviewEntity;
import com.albayrak.microservices.core.animereview.datalayer.ReviewRepository;
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
class ReviewServiceApplicationTests {


	private static final int ANIME_ID_NO_CONTENT = 502;
	private static final int ANIME_ID_OKAY = 1;
	private static final int ANIME_ID_NOT_FOUND = 213;
	private static final String ANIME_ID_INVALID_STRING = "not-integer";
	private static final int ANIME_ID_INVALID_NEGATIVE_VALUE = -1;

	private static final int REVIEW_ID = 1;

	@Autowired
	private WebTestClient client;


	@Autowired
	private ReviewRepository repository; //added persistence

	@BeforeEach
	public void setupDb(){ //added persistence
		repository.deleteAll();
	}

	@Test
	public void getReviewByAnimeId(){

		int expectedLength = 3;

		//add the reviews to the repo
		ReviewEntity entity1 = new ReviewEntity(ANIME_ID_OKAY, 1, "author-1", "subject-1", "content-1");
		repository.save(entity1);
		ReviewEntity entity2 = new ReviewEntity(ANIME_ID_OKAY, 2, "author-2", "subject-2", "content-2");
		repository.save(entity2);
		ReviewEntity entity3 = new ReviewEntity(ANIME_ID_OKAY, 3, "author-3", "subject-3", "content-3");
		repository.save(entity3);

		assertEquals(expectedLength, repository.findByAnimeId(ANIME_ID_OKAY).size());

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
	public void createReview(){
		int expectedSize =1;
		//create the recommendaiton

		Review review = new Review(ANIME_ID_OKAY, REVIEW_ID,
				"Author " + REVIEW_ID, "Subject" + REVIEW_ID, "Content " + REVIEW_ID, "SA");


		//send the POST request
		client.post()
				.uri("/review/")
				.body(just(review), Review.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedSize, repository.findByAnimeId(ANIME_ID_OKAY).size());


	}

	@Test
	void deleteReviews(){

		//create a review entity

		ReviewEntity entity = new ReviewEntity(ANIME_ID_OKAY, REVIEW_ID, "author-1", "subject-1", "content-1");
		//save it
		repository.save(entity);

		//verify there are exactly 1  entity for anime id 1
		assertEquals(1, repository.findByAnimeId(ANIME_ID_OKAY).size());

		//send the DELETE request
		client.delete()
				.uri("/review?animeId=" + ANIME_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody();

		//verify there are no entities for anime id 1
		assertEquals(0, repository.findByAnimeId(ANIME_ID_OKAY).size());
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
