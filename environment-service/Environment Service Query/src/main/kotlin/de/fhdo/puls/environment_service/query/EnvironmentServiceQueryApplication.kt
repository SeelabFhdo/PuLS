package de.fhdo.puls.environment_service.query

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class EnvironmentServiceQueryApplication

fun main(args: Array<String>) {
    runApplication<EnvironmentServiceQueryApplication>(*args)
}
