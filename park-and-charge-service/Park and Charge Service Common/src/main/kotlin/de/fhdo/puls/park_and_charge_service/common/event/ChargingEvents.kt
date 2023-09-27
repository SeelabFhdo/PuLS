package de.fhdo.puls.park_and_charge_service.common.event

import java.util.*

data class ChargingInformationEvent(
    val chargingStationId: String,
    val current_L1: Float,
    val current_L2: Float,
    val current_L3: Float,
    val voltage_L1: Float,
    val voltage_L2: Float,
    val voltage_L3: Float,
    val timeStamp: Date
)

data class StatusInformationEvent(
    val chargingStationId: String,
    val chargedEnergy: Float,
    val userId: Long,
    val currentLimit: Float,
    val timeStamp: Date,
    val chargingStatus: ChargingStatus
)

data class KeepAliveInformationEvent(
    val chargingStationId: String,
    val timeStamp: Date,
    val chargingStatus: ChargingStatus,
    val gridStatus: Float
)

enum class ChargingStatus(val status: Int) {
    STATUS1(1),
    STATUS2(2),
    STATUS3(3),
    STATUS4(4)
}

data class ParkingStatusEvent(
    val timeStamp: Date,
    val parkingSensorId: String,
    val status: String
)