package de.fhdo.puls.park_and_charge_service.common.event

import de.fhdo.puls.park_and_charge_service.common.command.TimePeriod

class ElectrifiedParkingSpaceCreatedEvent (
    val parkingSpaceId: String,
    val name: String,
    val chargingStationId: String,
    val description: String,
    val ownerId: Long,
    val parkingPricePerHour: Float,
    val activated: Boolean,
    val blocked: Boolean,
    val offered: Boolean,
    val longitude: Double,
    val latitude: Double,
    val parkingSpaceSize: String,
    val chargingPricePerKWH: Float,
    val chargingType: String,
    val pluginType: String,
    val availablePeriods: List<TimePeriod>,
    val parkingSensorId: String?
) {
    override fun toString(): String {
        return "ElectricParkingSpaceCreatedEvent(parkingSpaceId='$parkingSpaceId', " +
            "name='$name', description='$description', ownerId=$ownerId, " +
            "parkingPricePerHour=$parkingPricePerHour, activated=$activated, blocked=$blocked, " +
            "offered=$offered, longitude=$longitude, latitude=$latitude, " +
            "chargingPricePerHour=$chargingPricePerKWH, chargingType='$chargingType', " +
            "pluginType='$pluginType', availablePeriods=$availablePeriods)"
    }
}