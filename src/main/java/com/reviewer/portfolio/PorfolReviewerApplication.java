package com.reviewer.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PorfolReviewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PorfolReviewerApplication.class, args);
	}

}
