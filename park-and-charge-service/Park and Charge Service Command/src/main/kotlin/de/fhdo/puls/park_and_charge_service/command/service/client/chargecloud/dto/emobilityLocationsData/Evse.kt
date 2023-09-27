package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilityLocationsData

data class Evse(
        val connectors: List<Connector> = listOf(),
        val roaming: Boolean = false,
        val uid: String = ""
)