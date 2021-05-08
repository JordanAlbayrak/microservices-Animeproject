package com.albayrak.microservices.core.animerecommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.albayrak")
public class AnimeRecommendationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeRecommendationServiceApplication.class, args);
	}

}
