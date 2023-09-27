package de.fhdo.puls.booking_service.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookingServiceCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingServiceCommandApplication.class, args);
	}

}
