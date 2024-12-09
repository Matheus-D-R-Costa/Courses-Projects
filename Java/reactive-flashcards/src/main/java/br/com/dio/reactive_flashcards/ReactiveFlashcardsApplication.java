package br.com.dio.reactive_flashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class ReactiveFlashcardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveFlashcardsApplication.class, args);
	}

}
