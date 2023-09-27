package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilityLocationsData

data class Data(
        val coordinates: Coordinates = Coordinates(),
        val distance_in_m: String = "",
        val evses: List<Evse> = listOf(),
        val id: String = "",
        val opening_times: OpeningTimes = OpeningTimes(),
        val operatorId: String = "",
        val roaming: Boolean = false,
        val tariffZones: List<String> = listOf()
)