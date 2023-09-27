package de.fhdo.puls.booking_service.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookingServiceQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingServiceQueryApplication.class, args);
	}

}
