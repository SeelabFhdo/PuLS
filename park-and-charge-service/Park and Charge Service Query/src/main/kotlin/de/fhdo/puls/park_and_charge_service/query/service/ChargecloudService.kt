package de.fhdo.puls.park_and_charge_service.query.service

import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudParkingSpace
import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudParkingSpacesSyncedEvent
import de.fhdo.puls.park_and_charge_service.query.domain.ChargecloudParkingSpaceAggregate
import de.fhdo.puls.park_and_charge_service.query.domain.ChargecloudParkingSpaceValueObject
import de.fhdo.puls.park_and_charge_service.query.domain.ChargecloudPlugInformation
import de.fhdo.puls.park_and_charge_service.query.domain.Location
import de.fhdo.puls.park_and_charge_service.query.repository.ChargecloudParkingSpaceRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChargecloudService {
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var repository: ChargecloudParkingSpaceRepository

    fun handleChargecloudParkingSpacesSyncedEvent(event: ChargecloudParkingSpacesSyncedEvent) {
        repository.deleteAll()

        for (parkingSpace in event.parkingSpaces) {
            var aggregate = aggregateFromEvent(parkingSpace)
            repository.save(aggregate)
        }

        logger.info { "Saved ${event.parkingSpaces.size} chargecloud parkingspace aggregates" }
    }

    fun getAllChargecloudParkingSpaces() : List<ChargecloudParkingSpaceValueObject> {
        val parkingSpaces = repository.findAll().toList()
        return parkingSpaces.map { valueObjectFromAggregate(it) }
    }

    fun getChargecloudParkingSpaceById(id: String) : ChargecloudParkingSpaceValueObject {
        return valueObjectFromAggregate(repository.findById(id).orElse(null))
    }

    private fun valueObjectFromAggregate(aggregate: ChargecloudParkingSpaceAggregate) : ChargecloudParkingSpaceValueObject {
        return ChargecloudParkingSpaceValueObject(id = aggregate.id ?: "null",
                originalId = aggregate.originalId,
                name = aggregate.name,
                operatorId = aggregate.operatorId,
                operatorName = aggregate.operatorName,
                locationId = aggregate.locationId,
                latitude = aggregate.location.latitude,
                longitude = aggregate.location.longitude,
                address_street = aggregate.address_street,
                address_city = aggregate.address_city,
                address_country = aggregate.address_country,
                address_zip = aggregate.address_zip,
                plugs = aggregate.plugs.map { ChargecloudPlugInformation(plugType = it.plugType,
                        plugMaxPower = it.plugMaxPower,
                        plugPowerType = it.plugPowerType,
                        roaming = it.roaming) },
                createdDate = aggregate.createdDate,
                lastModifiedDate = aggregate.lastModifiedDate)
    }
    private fun aggregateFromEvent(event: ChargecloudParkingSpace) : ChargecloudParkingSpaceAggregate {
        return ChargecloudParkingSpaceAggregate(id = null,
                originalId = event.commandId,
                name = event.name,
                operatorId = event.operatorId,
                operatorName = event.operatorName,
                locationId = event.locationId,
                location = Location(event.longitude,
                        event.latitude),
                address_street = event.address_street,
                address_city = event.address_city,
                address_country = event.address_country,
                address_zip = event.address_zip,

                plugs = event.plugs.map { ChargecloudPlugInformation(plugType = it.plugType,
                    plugMaxPower = it.plugMaxPower,
                    plugPowerType = it.plugPowerType,
                    roaming = it.roaming) },

                createdDate = Date(),
                lastModifiedDate = Date())
    }
}