package com.tinqinacademy.hotel.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.tinqinacademy.hotel")
//@EnableJdbcRepositories(basePackages = "com.tinqinacademy.hotel.persistence.repository.interfaces")
@EnableJpaRepositories(basePackages = "com.tinqinacademy.hotel.persistence.repository.interfaces")
@EntityScan(basePackages = "com.tinqinacademy.hotel.persistence.entities")
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

}
