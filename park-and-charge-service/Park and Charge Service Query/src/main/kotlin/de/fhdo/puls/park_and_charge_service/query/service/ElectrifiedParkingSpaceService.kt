package de.fhdo.puls.park_and_charge_service.query.service

import de.fhdo.puls.park_and_charge_service.common.event.*
import de.fhdo.puls.park_and_charge_service.query.domain.*
import de.fhdo.puls.park_and_charge_service.query.repository.ChargingInformationRepository
import de.fhdo.puls.park_and_charge_service.query.repository.ElectrifiedParkingSpaceRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.*

@Service
class ElectrifiedParkingSpaceService {
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var electrifiedParkingSpaceRepository: ElectrifiedParkingSpaceRepository

    @Autowired
    lateinit var chargingInformationRepository: ChargingInformationRepository

    fun handleElectrifiedParkingSpaceCreatedEvent(event: ElectrifiedParkingSpaceCreatedEvent) {
        var aggregate = aggregateFromElectrifiedParkingSpaceCreatedEvent(event)
        aggregate = electrifiedParkingSpaceRepository.save(aggregate)
        val information = ChargingInformation(null, aggregate.chargingStationId)
        chargingInformationRepository.save(information)
        logger.info { "Saved aggregate: ${aggregate.toString()}" }
    }

    fun handleCPIChangeEvent(event: CPIChangeEvent) {
        val aggregateOptional = electrifiedParkingSpaceRepository.findById(event.id_query)
        if (aggregateOptional.isPresent) {
            val aggregate = aggregateOptional.get()
            aggregate.name = event.name
            aggregate.activated = event.activated
            electrifiedParkingSpaceRepository.save(aggregate)
        } else {
            throw Exception("Object not found.")
        }
    }

    fun valueObjectFromElectrifiedParkingSpaceAggregate(
        aggregate: ElectrifiedParkingSpaceAggregate
    ): ElectrifiedParkingSpaceValueObject {
        return ElectrifiedParkingSpaceValueObject(
            aggregate.id ?: "null", aggregate.chargingStationId, aggregate.originalId,
            aggregate.name, aggregate.description, aggregate.ownerId, aggregate.parkingPricePerHour,
            aggregate.createdDate, aggregate.lastModifiedDate, aggregate.activated,
            aggregate.blocked, aggregate.offered, aggregate.location.longitude,
            aggregate.location.latitude, aggregate.availabilityPeriods,
            aggregate.parkingSpaceSize.toString(), aggregate.chargingPricePerKWH,
            aggregate.chargingType.toString(), aggregate.pluginType, aggregate.parkingSenorId
        )
    }

    fun findElectrifiedParkingSpacesByIds(ids: List<String>): List<ElectrifiedParkingSpaceValueObject> {
        val electrifiedParkingSpacesList = LinkedList<ElectrifiedParkingSpaceValueObject>()
        ids.forEach {
            val aggregate = electrifiedParkingSpaceRepository.findById(it)
            if (aggregate.isPresent)
                electrifiedParkingSpacesList.add(valueObjectFromElectrifiedParkingSpaceAggregate(aggregate.get()))
        }
        return electrifiedParkingSpacesList
    }

    fun findElectrifiedParkingSpacesByOriginalIds(ids: List<String>): List<ElectrifiedParkingSpaceValueObject> {
        val electrifiedParkingSpacesList = LinkedList<ElectrifiedParkingSpaceValueObject>()
        ids.forEach {
            val aggregate = electrifiedParkingSpaceRepository.findByOriginalId(it)
            if (aggregate.isPresent)
                electrifiedParkingSpacesList.add(valueObjectFromElectrifiedParkingSpaceAggregate(aggregate.get()))
        }
        return electrifiedParkingSpacesList
    }

    private fun aggregateFromElectrifiedParkingSpaceCreatedEvent(
        event: ElectrifiedParkingSpaceCreatedEvent
    ): ElectrifiedParkingSpaceAggregate {
        return ElectrifiedParkingSpaceAggregate(
            null, event.chargingStationId, event.parkingSpaceId, event.name,
            event.description, event.ownerId, event.parkingPricePerHour, Date(), Date(),
            event.activated, event.blocked, event.offered, Location(event.longitude, event.latitude),
            event.availablePeriods.map { TimePeriod(it.start, it.end) },
            ParkingSpaceSize.valueOf(event.parkingSpaceSize), event.chargingPricePerKWH,
            ChargingType.valueOf(event.chargingType), event.pluginType, event.parkingSensorId!!
        )
    }

    fun handleChargingInformationEvent(event: ChargingInformationEvent) {
        logger.info { "Handle ChargingInformationEvent." }
        val information = chargingInformationRepository.findByChargingStationId(event.chargingStationId)
        information.currentL1Data.add(SensorValue(event.current_L1.toString(), event.timeStamp))
        information.currentL2Data.add(SensorValue(event.current_L2.toString(), event.timeStamp))
        information.currentL3Data.add(SensorValue(event.current_L3.toString(), event.timeStamp))
        information.voltageL1Data.add(SensorValue(event.voltage_L1.toString(), event.timeStamp))
        information.voltageL2Data.add(SensorValue(event.voltage_L2.toString(), event.timeStamp))
        information.voltageL3Data.add(SensorValue(event.voltage_L3.toString(), event.timeStamp))
        chargingInformationRepository.save(information)

    }

    fun handleStatusInformationEvent(event: StatusInformationEvent) {
        logger.info { "Handle StatusInformationEvent." }
        val information = chargingInformationRepository.findByChargingStationId(event.chargingStationId)
        information.chargingInformationData.add(createChargingInformationDataFromEvent(event))
        chargingInformationRepository.save(information)
    }

    private fun createChargingInformationDataFromEvent(event: StatusInformationEvent): ChargingInformationData {
        return ChargingInformationData(
            event.chargedEnergy,
            event.userId,
            event.currentLimit,
            event.timeStamp,
            event.chargingStatus
        )
    }

    fun handleKeepAliveInformationEvent(event: KeepAliveInformationEvent) {
        logger.info { "Handle KeepAliveInformationEvent." }
        val information = chargingInformationRepository.findByChargingStationId(event.chargingStationId)
        information.keepAliveData.add(KeepAliveInformation(event.timeStamp, event.gridStatus))
        chargingInformationRepository.save(information)
    }

    @Throws(Exception::class)
    fun getChargingStationInformation(chargingStationId: String, infoType: String): List<SensorValue> {
        val chargingInformation = chargingInformationRepository.findByChargingStationId(chargingStationId)

        return when(infoType) {
            "current_L1" -> chargingInformation.currentL1Data
            "current_L2" -> chargingInformation.currentL2Data
            "current_L3" -> chargingInformation.currentL3Data
            "voltage_L1" -> chargingInformation.voltageL1Data
            "voltage_L2" -> chargingInformation.voltageL2Data
            "voltage_L3" -> chargingInformation.voltageL3Data
            else -> throw Exception("No data found for $infoType")
        }
    }

    fun getStatusInformationData(chargingStationId: String): List<ChargingInformationData> {
        val chargingInformation = chargingInformationRepository.findByChargingStationId(chargingStationId)
        return chargingInformation.chargingInformationData
    }

    fun getKeepAliveInformation(chargingStationId: String): List<KeepAliveInformation> {
        val chargingInformation = chargingInformationRepository.findByChargingStationId(chargingStationId)
        return chargingInformation.keepAliveData
    }

    fun handleParkingStatusEvent(event: ParkingStatusEvent) {
        val aggregate = electrifiedParkingSpaceRepository
            .findElectrifiedParkingSpaceAggregateByParkingSenorId(event.parkingSensorId)
        val information = chargingInformationRepository.findByChargingStationId(aggregate.chargingStationId)
        val parkingStatus = parkingStatusInformationFromEvent(event)
        information.parkingInformationData.add(parkingStatus)
        chargingInformationRepository.save(information)
    }

    private fun parkingStatusInformationFromEvent(event: ParkingStatusEvent): ParkingStatusInformation {
        return ParkingStatusInformation(event.timeStamp, ParkingStatus.valueOf(event.status))
    }

    fun getCurrentChargingStationStatus(chargingStationId: String): ChargingStatus {
        val chargingInfo = chargingInformationRepository.findByChargingStationId(chargingStationId)
        val latestChargingStatus = chargingInfo.currentL1Data.last()
        val latestKeepAliveStatus = chargingInfo.keepAliveData.last()

        return if (latestChargingStatus.timeStamp.after(latestKeepAliveStatus.timeStamp))
            ChargingStatus.STATUS4
        else
            ChargingStatus.STATUS1
    }

    fun getParkingInformation(chargingStationId: String): List<ParkingStatusInformation> {
        val information = chargingInformationRepository.findByChargingStationId(chargingStationId)
        return information.parkingInformationData
    }
}