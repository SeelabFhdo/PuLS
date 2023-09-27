package de.fhdo.puls.park_and_charge_service.query

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ParkAndChargeServiceQueryApplication

fun main(args: Array<String>) {
	runApplication<ParkAndChargeServiceQueryApplication>(*args)
}
