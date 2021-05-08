package com.albayrak.microservices.core.animereview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.albayrak")
public class AnimeReviewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeReviewServiceApplication.class, args);
	}

}
