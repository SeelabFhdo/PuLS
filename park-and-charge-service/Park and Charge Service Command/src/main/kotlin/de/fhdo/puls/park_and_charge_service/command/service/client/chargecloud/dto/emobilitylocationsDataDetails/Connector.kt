package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilitylocationsDataDetails

data class Connector(
    val ampere: String = "",
    val format: String = "",
    val id: String = "",
    val max_power: Int = 0,
    val power_type: String = "",
    val standard: String = "",
    val status: String = "",
    val tariff_id: String = "",
    val voltage: String = ""
)