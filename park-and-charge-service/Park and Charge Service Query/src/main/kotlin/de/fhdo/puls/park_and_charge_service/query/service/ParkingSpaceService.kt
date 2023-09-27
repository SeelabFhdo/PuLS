package de.fhdo.puls.park_and_charge_service.query.service

import de.fhdo.puls.park_and_charge_service.common.event.ParkingSpaceCreatedEvent
import de.fhdo.puls.park_and_charge_service.query.domain.Location
import de.fhdo.puls.park_and_charge_service.query.domain.ParkingSpaceAggregate
import de.fhdo.puls.park_and_charge_service.query.domain.ParkingSpaceSize
import de.fhdo.puls.park_and_charge_service.query.domain.ParkingSpaceValueObject
import de.fhdo.puls.park_and_charge_service.query.domain.TimePeriod
import de.fhdo.puls.park_and_charge_service.query.repository.ParkingSpaceRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParkingSpaceService {
    private val logger = KotlinLogging.logger {}
    @Autowired
    lateinit var parkingSpaceRepository: ParkingSpaceRepository
    fun handleParkingSpaceCreatedEvent(event: ParkingSpaceCreatedEvent) {
        val aggregate = aggregateFromParkingSpaceCreatedEvent(event)
        logger.info { "Aggregate saved: ${aggregate.toString()}" }
        parkingSpaceRepository.save(aggregate)
    }

    fun valueObjectFromParkingSpaceAggregate(aggregate: ParkingSpaceAggregate):
        ParkingSpaceValueObject {
        return ParkingSpaceValueObject(aggregate.id?: "Not set.", aggregate.originalId,
                aggregate.name, aggregate.description, aggregate.ownerId,
                aggregate.parkingPricePerHour, aggregate.createdDate, aggregate.lastModifiedDate,
                aggregate.activated, aggregate.blocked, aggregate.offered,
                aggregate.location.longitude, aggregate.location.latitude,
                aggregate.availabilityPeriods, aggregate.parkingSpaceSize.toString())
    }

    fun findParkingSpacesByOriginalIds(ids: List<String>): List<ParkingSpaceValueObject> {
        val parkingSpacesList = LinkedList<ParkingSpaceValueObject>()
        ids.forEach {
            val aggregate = parkingSpaceRepository.findByOriginalId(it)
            if (aggregate.isPresent)
                parkingSpacesList.add(valueObjectFromParkingSpaceAggregate(aggregate.get()))
        }
        return parkingSpacesList
    }

    private fun aggregateFromParkingSpaceCreatedEvent(event: ParkingSpaceCreatedEvent):
        ParkingSpaceAggregate {
        return ParkingSpaceAggregate(null, event.parkingSpaceId, event.name, event.description,
            event.ownerId, event.parkingPricePerHour, Date(), Date(), event.activated,
            event.blocked, event.offered, Location(event.longitude, event.latitude),
            event.availablePeriods.map { TimePeriod(it.start, it.end) },
            ParkingSpaceSize.valueOf(event.parkingSpaceSize))
    }

    fun findParkingSpacesByIds(ids: List<String>): List<ParkingSpaceValueObject> {
        val parkingSpacesList = LinkedList<ParkingSpaceValueObject>()
        ids.forEach {
            val aggregate = parkingSpaceRepository.findById(it)
            if (aggregate.isPresent)
                parkingSpacesList.add(valueObjectFromParkingSpaceAggregate(aggregate.get()))
        }
        return parkingSpacesList
    }
}