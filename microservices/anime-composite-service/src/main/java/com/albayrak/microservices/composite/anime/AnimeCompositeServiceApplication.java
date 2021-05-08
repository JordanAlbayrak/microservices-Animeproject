package com.albayrak.microservices.composite.anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.albayrak")
@SpringBootApplication
public class AnimeCompositeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeCompositeServiceApplication.class, args);
	}

}
