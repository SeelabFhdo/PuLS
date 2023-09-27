package de.fhdo.puls.park_and_charge_service.command.message

import com.fasterxml.jackson.annotation.JsonProperty
import de.fhdo.puls.park_and_charge_service.common.event.ChargingStatus
import java.util.Date

data class StatusMessage(
    @JsonProperty("chargingPointId")
    val chargingStationId: String,
    val chargedEnergy: Float,
    @JsonProperty("current_limit")
    val currentLimit: Float,
    @JsonProperty("timestamp")
    val timeStamp: Date,
    @JsonProperty("status")
    val chargingStatus: ChargingStatus,
    val chargingDuration: Float
)