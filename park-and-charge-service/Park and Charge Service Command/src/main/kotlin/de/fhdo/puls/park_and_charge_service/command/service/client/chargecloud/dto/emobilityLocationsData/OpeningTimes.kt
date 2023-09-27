package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilityLocationsData

data class OpeningTimes(
        val regular_hours: List<RegularHour>? = null,
        val twentyfourseven: Boolean = false
)