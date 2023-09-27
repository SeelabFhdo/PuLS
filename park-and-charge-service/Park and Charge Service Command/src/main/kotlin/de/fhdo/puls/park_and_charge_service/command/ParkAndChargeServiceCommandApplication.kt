package de.fhdo.puls.park_and_charge_service.command

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ParkAndChargeServiceCommandApplication

fun main(args: Array<String>) {
	runApplication<ParkAndChargeServiceCommandApplication>(*args)
}
