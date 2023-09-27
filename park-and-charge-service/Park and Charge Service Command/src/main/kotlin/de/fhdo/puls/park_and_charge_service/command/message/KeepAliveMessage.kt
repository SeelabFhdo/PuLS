package de.fhdo.puls.park_and_charge_service.command.message

import com.fasterxml.jackson.annotation.JsonProperty
import de.fhdo.puls.park_and_charge_service.common.event.ChargingStatus
import java.util.Date

data class KeepAliveMessage(
    @JsonProperty("status")
    val chargingStatus: ChargingStatus,
    @JsonProperty("chargingPointId")
    val chargingStationId: String,
    @JsonProperty("timestamp")
    val timeStamp: Date,
    @JsonProperty("gridstate")
    val gridState: Float
)
