package de.fhdo.puls.environment_service.command

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class EnvironmentServiceCommandApplication

fun main(args: Array<String>) {
    runApplication<EnvironmentServiceCommandApplication>(*args)
}