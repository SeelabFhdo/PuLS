package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilitylocationsDataDetails

data class Evse(
    val capabilities: List<Any> = listOf(),
    val chargePointParkingSpaceNumbers: Any? = null,
    val chargePointPosition: String? = "",
    val chargePointPublicComment: Any? = null,
    val chargingStationPosition: Any? = null,
    val connectors: List<Connector> = listOf(),
    val floor_level: Any? = null,
    val id: String = "",
    val physical_reference: Any? = null,
    val roaming: Boolean = false,
    val status: String = "",
    val uid: String = "",
    val vehicle_type: String = ""
)