package com.questionpro.proj.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
@EnableJpaRepositories
public class HackerNewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackerNewsServiceApplication.class, args);
	}

}
