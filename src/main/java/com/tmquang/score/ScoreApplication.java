package com.tmquang.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreApplication.class, args);
	}

}
