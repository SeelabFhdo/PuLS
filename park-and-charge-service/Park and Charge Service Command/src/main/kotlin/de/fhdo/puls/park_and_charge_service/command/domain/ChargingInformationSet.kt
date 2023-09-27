package de.fhdo.puls.park_and_charge_service.command.domain

import de.fhdo.puls.park_and_charge_service.command.message.ParkingStatus
import de.fhdo.puls.park_and_charge_service.common.event.ChargingStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class ChargingInformationSet(
    @Id
    val id: String?,
    val chargingStationId: String
) {
    var chargingInformation  = mutableListOf<ChargingInformation>()
    var statusInformation = mutableListOf<StatusInformation>()
    var keepAliveInformation = mutableListOf<KeepAliveInformation>()
    var parkingInformation = mutableListOf<ParkingInformation>()
}

class ChargingInformation(
    val current_L1: Float,
    val current_L2: Float,
    val current_L3: Float,
    val voltage_L1: Float,
    val voltage_L2: Float,
    val voltage_L3: Float,
    val timeStamp: Date
)

class StatusInformation(
    val chargedEnergy: Float,
    val currentLimit: Float,
    val timeStamp: Date,
    val chargingStatus: ChargingStatus
)

data class ChargingInformationValueObject(
    val chargingStationId: String,
    val current_L1: Float,
    val current_L2: Float,
    val current_L3: Float,
    val voltage_L1: Float,
    val voltage_L2: Float,
    val voltage_L3: Float,
    val timeStamp: Date
)

data class StatusInformationValueObject(
    val chargingStationId: String,
    val chargedEnergy: Float,
    val userId: Long,
    val currentLimit: Float,
    val timeStamp: Date,
    val chargingStatus: ChargingStatus
)

data class KeepAliveInformation(
    val timeStamp: Date,
    val gridStatus: Float
)

data class ParkingInformation(
    val timeStamp: Date,
    val status: ParkingStatus
)