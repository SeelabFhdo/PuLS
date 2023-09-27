package de.fhdo.puls.chargeclouddataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ChargecloudDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargecloudDataServiceApplication.class, args);
	}

}
