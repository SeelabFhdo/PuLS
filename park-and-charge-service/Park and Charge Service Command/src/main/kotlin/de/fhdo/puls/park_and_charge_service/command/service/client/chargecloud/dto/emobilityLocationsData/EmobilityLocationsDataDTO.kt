package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilityLocationsData

data class EmobilityLocationsDataDTO(
        val `data`: List<Data> = listOf(),
        val dataType: String = "",
        val errorCode: Any? = null,
        val messageLocalized: Any? = null,
        val success: Boolean = false
)