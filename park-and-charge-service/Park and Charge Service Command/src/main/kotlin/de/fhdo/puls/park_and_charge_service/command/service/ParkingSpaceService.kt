package de.fhdo.puls.park_and_charge_service.command.service

import de.fhdo.puls.park_and_charge_service.command.configuration.DefaultTopicKafkaTemplate
import de.fhdo.puls.park_and_charge_service.command.domain.Location
import de.fhdo.puls.park_and_charge_service.command.domain.ParkingSpaceAggregate
import de.fhdo.puls.park_and_charge_service.command.domain.ParkingSpaceSize
import de.fhdo.puls.park_and_charge_service.command.domain.TimePeriod
import de.fhdo.puls.park_and_charge_service.command.repository.ParkingSpaceRepository
import de.fhdo.puls.park_and_charge_service.common.command.CreateParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.command.UpdateParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.event.ParkingSpaceCreatedEvent
import de.fhdo.puls.park_and_charge_service.common.event.ParkingSpaceUpdatedEvent
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ParkingSpaceService {
    private val logger = KotlinLogging.logger {}
    @Autowired
    lateinit var parkingSpaceRepository: ParkingSpaceRepository

    @Value("\${kafka.topic.parkingspace.created}")
    lateinit var parkingSpaceCreatedTopic: String

    @Value("\${kafka.topic.parkingspace.updated}")
    lateinit var parkingSpaceUpdatedTopic: String

    @Qualifier("parkingSpaceCreatedEventProducer")
    @Autowired
    lateinit var createdEventProducer: DefaultTopicKafkaTemplate<String, ParkingSpaceCreatedEvent>

    @Qualifier("parkingSpaceUpdatedEventProducer")
    @Autowired
    lateinit var updatedEventProducer: DefaultTopicKafkaTemplate<String, ParkingSpaceUpdatedEvent>

    fun handleParkingSpaceCreatedCommand(command: CreateParkingSpaceCommand) {
        var aggregate = aggregateFromCreateParkingSpaceCommand(command)
        aggregate = parkingSpaceRepository.save(aggregate)
        val event = createParkingSpaceEventFromAggregate(aggregate)
        createdEventProducer.send(parkingSpaceCreatedTopic, event)
        logger.info { "Event send: ${event.toString()}" }
    }

    fun handleParkingSpaceUpdatedCommand(command: UpdateParkingSpaceCommand) {
        var aggregate = aggregateFromUpdateParkingSpaceCommand(command)
        aggregate = parkingSpaceRepository.save(aggregate); // overrides/updates the old entry in the db
        val event = updateParkingSpaceEventFromAggregate(aggregate)
        updatedEventProducer.send(parkingSpaceUpdatedTopic, event)
        logger.info { "Event send: ${event.toString()}" }
    }

    private fun aggregateFromCreateParkingSpaceCommand(command: CreateParkingSpaceCommand):
        ParkingSpaceAggregate {
        return ParkingSpaceAggregate(null, command.name, command.description, command.ownerId,
            command.parkingPricePerHour, Date(), Date(), command.activated, command.blocked,
            command.offered, Location(command.longitude, command.latitude),
            command.availablePeriods.map { a -> TimePeriod(a.start, a.end)}.toList(),
            ParkingSpaceSize.valueOf(command.parkingSpaceSize))
    }

    private fun aggregateFromUpdateParkingSpaceCommand(command: UpdateParkingSpaceCommand):
            ParkingSpaceAggregate {
        return ParkingSpaceAggregate(null, command.name, command.description, command.ownerId,
                command.parkingPricePerHour, Date(), Date(), command.activated, command.blocked,
                command.offered, Location(command.longitude, command.latitude),
                command.availablePeriods.map { a -> TimePeriod(a.start, a.end)}.toList(),
                ParkingSpaceSize.valueOf(command.parkingSpaceSize))
    }

    private fun createParkingSpaceEventFromAggregate(aggregate: ParkingSpaceAggregate):
        ParkingSpaceCreatedEvent {
        return ParkingSpaceCreatedEvent(aggregate.id?: "MissingCommandId",
            aggregate.name, aggregate.description, aggregate.ownerId, aggregate.parkingPricePerHour,
            aggregate.activated, aggregate.blocked, aggregate.offered, aggregate.location.longitude,
            aggregate.location.latitude, aggregate.parkingSpaceSize.toString(),
            aggregate.availabilityPeriods.map { a ->
                de.fhdo.puls.park_and_charge_service.common.command.TimePeriod(a.start, a.end) })
    }

    private fun updateParkingSpaceEventFromAggregate(aggregate: ParkingSpaceAggregate):
            ParkingSpaceUpdatedEvent {
        return ParkingSpaceUpdatedEvent(aggregate.id?: "MissingCommandId",
                aggregate.name, aggregate.description, aggregate.ownerId, aggregate.parkingPricePerHour,
                aggregate.activated, aggregate.blocked, aggregate.offered, aggregate.location.longitude,
                aggregate.location.latitude, aggregate.parkingSpaceSize.toString(),
                aggregate.availabilityPeriods.map { a ->
                    de.fhdo.puls.park_and_charge_service.common.command.TimePeriod(a.start, a.end) })
    }
}
