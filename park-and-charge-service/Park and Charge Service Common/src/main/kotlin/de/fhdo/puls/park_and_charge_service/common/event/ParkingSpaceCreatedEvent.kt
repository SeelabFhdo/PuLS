package de.fhdo.puls.park_and_charge_service.common.event

import de.fhdo.puls.park_and_charge_service.common.command.TimePeriod

class ParkingSpaceCreatedEvent (
    val parkingSpaceId: String,
    val name: String,
    val description: String,
    val ownerId: Long,
    val parkingPricePerHour: Float,
    val activated: Boolean,
    val blocked: Boolean,
    val offered: Boolean,
    val longitude: Double,
    val latitude: Double,
    val parkingSpaceSize: String,
    val availablePeriods: List<TimePeriod>
) {
    override fun toString(): String {
        return "ParkingSpaceCreatedEvent(parkingSpaceId='$parkingSpaceId', name='$name', " +
                "description='$description', ownerId=$ownerId, " +
                "parkingPricePerHour=$parkingPricePerHour, activated=$activated, blocked=$blocked, " +
                "offered=$offered, longitude=$longitude, latitude=$latitude, " +
                "parkingSpaceSize='$parkingSpaceSize', availablePeriods=$availablePeriods)"
    }
}