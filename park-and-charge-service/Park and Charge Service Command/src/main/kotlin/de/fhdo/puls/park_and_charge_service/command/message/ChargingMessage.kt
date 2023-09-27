package de.fhdo.puls.park_and_charge_service.command.message

import com.fasterxml.jackson.annotation.JsonProperty
import de.fhdo.puls.park_and_charge_service.common.event.ChargingStatus
import java.util.Date

data class ChargingMessage(
    @JsonProperty("chargingPointId")
    val chargingStationId: String,
    @JsonProperty("status")
    val chargingStatus: ChargingStatus,
    val current_L1: Float,
    val current_L2: Float,
    val current_L3: Float,
    val voltage_L1: Float,
    val voltage_L2: Float,
    val voltage_L3: Float,
    @JsonProperty("current_limit")
    val currentLimit: Float,
    @JsonProperty("timestamp")
    val timeStamp: Date
)