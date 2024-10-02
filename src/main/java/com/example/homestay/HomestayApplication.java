package com.example.homestay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HomestayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomestayApplication.class, args);
	}

}
