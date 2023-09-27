package de.fhdo.puls.user_management_service.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserManagementServiceQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceQueryApplication.class, args);
    }
}
