package de.fhdo.puls.park_and_charge_service.query.domain

import de.fhdo.puls.park_and_charge_service.common.event.ChargingStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class ChargingInformation(
    @Id
    val id: String?,
    val chargingStationId: String
) {
    var currentL1Data = mutableListOf<SensorValue>()
    var currentL2Data = mutableListOf<SensorValue>()
    var currentL3Data = mutableListOf<SensorValue>()
    var voltageL1Data = mutableListOf<SensorValue>()
    var voltageL2Data = mutableListOf<SensorValue>()
    var voltageL3Data = mutableListOf<SensorValue>()
    var keepAliveData = mutableListOf<KeepAliveInformation>()
    var chargingInformationData = mutableListOf<ChargingInformationData>()
    var parkingInformationData = mutableListOf<ParkingStatusInformation>()
}

data class SensorValue(
    val value: String,
    val timeStamp: Date
)

data class ChargingInformationData(
    val chargedEnergy: Float,
    val usedId: Long,
    val currentLimit: Float,
    val timeStamp: Date,
    val chargingStatus: ChargingStatus
)

data class KeepAliveInformation(
    val timeStamp: Date,
    val gridStatus: Float
)

data class ParkingStatusInformation(
    val timeStamp: Date,
    val parkingStatus: ParkingStatus
)

enum class ParkingStatus(status: String){
    FREE("FREE"),
    USED("USED")
}