package com.rhkr8521.iccas_question;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IccasQuestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(IccasQuestionApplication.class, args);
	}

}
