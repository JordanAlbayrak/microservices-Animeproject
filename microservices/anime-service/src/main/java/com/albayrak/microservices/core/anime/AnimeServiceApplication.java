package com.albayrak.microservices.core.anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.albayrak")
public class AnimeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeServiceApplication.class, args);
	}

}
