package de.fhdo.puls.user_management_service.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserManagementServiceCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceCommandApplication.class, args);
    }
}
