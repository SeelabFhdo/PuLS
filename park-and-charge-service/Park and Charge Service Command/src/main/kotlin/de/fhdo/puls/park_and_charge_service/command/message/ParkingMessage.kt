package de.fhdo.puls.park_and_charge_service.command.message

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class ParkingMessage(
    val parkingSensorId: String,
    @JsonProperty("timestamp")
    val timeStamp: Date,
    val status: ParkingStatus
)

enum class ParkingStatus constructor(val status: Int) {
    FREE(0),
    USED(1)
}