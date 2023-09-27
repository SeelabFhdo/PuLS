package de.fhdo.puls.park_and_charge_service.command.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.fhdo.puls.park_and_charge_service.command.configuration.DefaultTopicKafkaTemplate
import de.fhdo.puls.park_and_charge_service.command.domain.*
import de.fhdo.puls.park_and_charge_service.command.message.*
import de.fhdo.puls.park_and_charge_service.command.repository.ChargingInformationSetRepository
import de.fhdo.puls.park_and_charge_service.command.repository.ElectrifiedChargingSpaceRepository
import de.fhdo.puls.park_and_charge_service.common.command.CreateElectrifiedParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.command.DeleteElectrifiedParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.command.UpdateElectrifiedParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.event.*
import mu.KotlinLogging
import org.apache.tomcat.util.json.JSONParser
import org.codehaus.jettison.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import java.util.Date.from

@Service
class ElectrifiedParkingSpaceService {
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var chargingInformationSetRepository: ChargingInformationSetRepository

    @Value("\${kafka.topic.electrifiedparkingspace.created}")
    lateinit var electrifiedParkingSpaceCreatedTopic: String

    @Value("\${kafka.topic.electrifiedparkingspace.updated}")
    lateinit var electrifiedParkingSpaceUpdatedTopic: String

    @Value("\${kafka.topic.electrifiedparkingspace.deleted}")
    lateinit var electrifiedParkingSpaceDeletedTopic: String

    @Value("\${kafka.topic.chargingevent}")
    lateinit var chargingEventTopic: String

    @Value("\${kafka.topic.keepaliveevent}")
    lateinit var keepAliveEventTopic: String

    @Value("\${kafka.topic.statusevent}")
    lateinit var statusEventTopic: String

    @Value("\${kafka.topic.parkingevent}")
    lateinit var parkingEventTopic: String

    @Autowired
    lateinit var electrifiedChargingSpaceRepository: ElectrifiedChargingSpaceRepository

    @Qualifier("electrifiedParkingSpaceCreatedEventProducer")
    @Autowired
    lateinit var producer: DefaultTopicKafkaTemplate<String, ElectrifiedParkingSpaceCreatedEvent>

    @Qualifier("statusInformationEventProducer")
    @Autowired
    lateinit var statusInformationEventProducer: DefaultTopicKafkaTemplate<String, StatusInformationEvent>

    @Qualifier("chargingInformationEventProducer")
    @Autowired
    lateinit var chargingInformationEventProducer: DefaultTopicKafkaTemplate<String, ChargingInformationEvent>

    @Qualifier("keepAliveInformationEventProducer")
    @Autowired
    lateinit var keepAliveInformationEventProducer: DefaultTopicKafkaTemplate<String, KeepAliveInformationEvent>

    @Qualifier("parkingEventProducer")
    @Autowired
    lateinit var parkingEventProducer: DefaultTopicKafkaTemplate<String, ParkingStatusEvent>

    fun handleCreateElectrifiedParkingSpaceCommand(command: CreateElectrifiedParkingSpaceCommand) {
        var aggregate = aggregateFromCreateElectrifiedParkingSpaceCommand(command)
        aggregate = electrifiedChargingSpaceRepository.save(aggregate)
        val informationSet = createInformationSetFromAggregate(aggregate)
        chargingInformationSetRepository.save(informationSet)
        val event = eventFromElectricParkingSpaceAggregate(aggregate)
        producer.send(electrifiedParkingSpaceCreatedTopic, event)
        logger.info { "Event send: ${event.toString()}" }
    }

    fun handleUpdateElectrifiedParkingSpaceCommand(command: UpdateElectrifiedParkingSpaceCommand) {
        var aggregate = aggregateFromUpdateElectrifiedParkingSpaceCommand(command)
        aggregate = electrifiedChargingSpaceRepository.save(aggregate)
        val informationSet = createInformationSetFromAggregate(aggregate)
        chargingInformationSetRepository.save(informationSet)
        val event = eventFromElectricParkingSpaceAggregate(aggregate)
        producer.send(electrifiedParkingSpaceUpdatedTopic, event)
        logger.info { "Event send: ${event.toString()}" }
    }

    fun handleDeleteElectrifiedParkingSpaceCommand(command: DeleteElectrifiedParkingSpaceCommand) {
        var aggregate = aggregateFromDeleteElectrifiedParkingSpaceCommand(command)
        aggregate = electrifiedChargingSpaceRepository.save(aggregate)
        val informationSet = createInformationSetFromAggregate(aggregate)
        chargingInformationSetRepository.save(informationSet)
        val event = eventFromElectricParkingSpaceAggregate(aggregate)
        producer.send(electrifiedParkingSpaceDeletedTopic, event)
        logger.info { "Event send: ${event.toString()}" }
    }

    private fun createInformationSetFromAggregate(aggregate: ElectrifiedParkingSpaceAggregate):
            ChargingInformationSet {
        return ChargingInformationSet(null, aggregate.chargingStationId)
    }

    private fun aggregateFromCreateElectrifiedParkingSpaceCommand(
        command: CreateElectrifiedParkingSpaceCommand
    ): ElectrifiedParkingSpaceAggregate {
        return ElectrifiedParkingSpaceAggregate(
            null, command.chargingStationId, command.name, command.description,
            command.ownerId, command.parkingPricePerHour, Date(), Date(), command.activated,
            command.blocked, command.offered, Location(command.longitude, command.latitude),
            command.availablePeriods.map { TimePeriod(it.start, it.end) },
            ParkingSpaceSize.valueOf(command.parkingSpaceSize), command.chargingPricePerKWH,
            ChargingType.valueOf(command.chargingType), command.pluginType, command.parkingSensorId
        )
    }

    private fun aggregateFromUpdateElectrifiedParkingSpaceCommand(
            command: UpdateElectrifiedParkingSpaceCommand
    ): ElectrifiedParkingSpaceAggregate {
        return ElectrifiedParkingSpaceAggregate(
                null, command.chargingStationId, command.name, command.description,
                command.ownerId, command.parkingPricePerHour, Date(), Date(), command.activated,
                command.blocked, command.offered, Location(command.longitude, command.latitude),
                command.availablePeriods.map { TimePeriod(it.start, it.end) },
                ParkingSpaceSize.valueOf(command.parkingSpaceSize), command.chargingPricePerKWH,
                ChargingType.valueOf(command.chargingType), command.pluginType, command.parkingSensorId
        )
    }

    private fun aggregateFromDeleteElectrifiedParkingSpaceCommand(
            command: DeleteElectrifiedParkingSpaceCommand
    ): ElectrifiedParkingSpaceAggregate {
        return ElectrifiedParkingSpaceAggregate(
                null, command.chargingStationId, command.name, command.description,
                command.ownerId, command.parkingPricePerHour, Date(), Date(), command.activated,
                command.blocked, command.offered, Location(command.longitude, command.latitude),
                command.availablePeriods.map { TimePeriod(it.start, it.end) },
                ParkingSpaceSize.valueOf(command.parkingSpaceSize), command.chargingPricePerKWH,
                ChargingType.valueOf(command.chargingType), command.pluginType, command.parkingSensorId
        )
    }

    private fun eventFromElectricParkingSpaceAggregate(aggregate: ElectrifiedParkingSpaceAggregate):
            ElectrifiedParkingSpaceCreatedEvent {
        return ElectrifiedParkingSpaceCreatedEvent(aggregate.id ?: "null", aggregate.name, aggregate.chargingStationId,
            aggregate.description, aggregate.ownerId, aggregate.parkingPricePerHour,
            aggregate.activated, aggregate.blocked, aggregate.offered, aggregate.location.longitude,
            aggregate.location.latitude, aggregate.parkingSpaceSize.toString(),
            aggregate.chargingPricePerKWH, aggregate.chargingType.toString(), aggregate.pluginType,
            aggregate.availabilityPeriods.map {
                de.fhdo.puls.park_and_charge_service.common.command.TimePeriod(it.start, it.end)
            }, aggregate.parkingSensorId)
    }

    fun handleIncomingChargingMessage(message: String) {
        logger.info("Handle Incoming Charging Message ${message}.")
        val mapper = jacksonObjectMapper()
        val chargingMessage = mapper.readValue<ChargingMessage>(message)
        val informationSet = chargingInformationSetRepository.findByChargingStationId(chargingMessage.chargingStationId)
        val chargingInformation = ChargingInformation(
            chargingMessage.current_L1, chargingMessage.current_L2,
            chargingMessage.current_L3, chargingMessage.voltage_L1, chargingMessage.voltage_L2,
            chargingMessage.voltage_L3, chargingMessage.timeStamp
        )
        informationSet.chargingInformation.add(chargingInformation)
        chargingInformationSetRepository.save(informationSet)
        val chargingInformationEvent = chargingInformationMessageToEvent(chargingMessage)
        chargingInformationEventProducer.send(chargingEventTopic, chargingInformationEvent)
    }

    private fun chargingInformationMessageToEvent(chargingMessage: ChargingMessage): ChargingInformationEvent {
        return ChargingInformationEvent(
            chargingMessage.chargingStationId,
            chargingMessage.current_L1, chargingMessage.current_L2, chargingMessage.current_L3,
            chargingMessage.voltage_L1, chargingMessage.voltage_L2, chargingMessage.voltage_L3,
            chargingMessage.timeStamp
        )
    }

    fun handleIncomingKeepAliveMessage(message: String) {
        logger.info("Handle Incoming KeepAlive Message ${message}.")
        val mapper = jacksonObjectMapper()
        val keepAliveMessage = mapper.readValue<KeepAliveMessage>(message)
        val informationSet = chargingInformationSetRepository.findByChargingStationId(
            keepAliveMessage.chargingStationId
        )
        informationSet.keepAliveInformation.add(
            KeepAliveInformation(keepAliveMessage.timeStamp, keepAliveMessage.gridState)
        )
        chargingInformationSetRepository.save(informationSet)
        val keepAliveInformationEvent = keepAliveMessageToEvent(keepAliveMessage)
        keepAliveInformationEventProducer.send(keepAliveEventTopic, keepAliveInformationEvent)
    }

    private fun keepAliveMessageToEvent(keepAliveMessage: KeepAliveMessage): KeepAliveInformationEvent {
        return KeepAliveInformationEvent(
            keepAliveMessage.chargingStationId, keepAliveMessage.timeStamp,
            keepAliveMessage.chargingStatus, keepAliveMessage.gridState
        )
    }

    fun handleIncomingStatusMessage(message: String) {
        logger.info("Handle Incoming Status Message ${message}.")
        val mapper = jacksonObjectMapper()
        val statusMessage = mapper.readValue<StatusMessage>(message)
        val informationSet = chargingInformationSetRepository.findByChargingStationId(statusMessage.chargingStationId)
        val statusInformation = StatusInformation(
            statusMessage.chargedEnergy, statusMessage.currentLimit, statusMessage.timeStamp,
            statusMessage.chargingStatus
        )
        informationSet.statusInformation.add(statusInformation)
        chargingInformationSetRepository.save(informationSet)
        val statusInformationEvent = statusInformationMessageToEvent(statusMessage)
        statusInformationEventProducer.send(statusEventTopic, statusInformationEvent)
    }

    private fun statusInformationMessageToEvent(statusMessage: StatusMessage): StatusInformationEvent {
        return StatusInformationEvent(
            statusMessage.chargingStationId, statusMessage.chargedEnergy,
            2, statusMessage.currentLimit, statusMessage.timeStamp, statusMessage.chargingStatus
        )
    }

    fun handleIncomingParkingSensorMessage(message: String) {
        val json = JSONObject(message)
        val interfaceId = json.get("interface_id")
        val state = json.get("payload")
        if (interfaceId == "c6243c7a-77c8-40f4-9788-85f76f53de2e") {

            val parkingStatus = if (state.toString() == "00")
                ParkingStatus.FREE
            else
                ParkingStatus.USED

            val parkingMessage = ParkingMessage(interfaceId.toString(), Date(), parkingStatus)
            val station = electrifiedChargingSpaceRepository.findElectrifiedParkingSpaceAggregateByParkingSensorId(
                parkingMessage.parkingSensorId)

            val informationSet = chargingInformationSetRepository.findByChargingStationId(station.chargingStationId)
            informationSet.parkingInformation.add(ParkingInformation(parkingMessage.timeStamp, parkingMessage.status))
            chargingInformationSetRepository.save(informationSet)
            val event = parkingStatusEventFromMessage(parkingMessage)
            parkingEventProducer.send(parkingEventTopic, event)
        }
    }

    private fun parkingStatusEventFromMessage(message: ParkingMessage): ParkingStatusEvent {
        return ParkingStatusEvent(message.timeStamp, message.parkingSensorId, message.status.toString())

    }
}
