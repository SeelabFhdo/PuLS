package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilitylocationsDataDetails

data class Data(
    val address: String = "",
    val city: String = "",
    val coordinates: Coordinates = Coordinates(),
    val country: String = "",
    val directions: Any? = null,
    val distance_in_m: String = "",
    val evses: List<Evse> = listOf(),
    val id: String = "",
    val name: String = "",
    val opening_times: OpeningTimes = OpeningTimes(),
    val `operator`: Operator = Operator(),
    val owner: Any? = null,
    val postal_code: String = "",
    val roaming: Boolean = false,
    val tariffZones: List<String> = listOf()
)