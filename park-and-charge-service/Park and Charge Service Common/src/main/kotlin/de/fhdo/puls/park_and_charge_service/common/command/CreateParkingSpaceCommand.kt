package de.fhdo.puls.park_and_charge_service.common.command

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "ParkingSpaceCreatedEvent",
        description = "Data structure for creating an parking spaces, containing general "
                + "information regarding its location or pricing.")
class CreateParkingSpaceCommand (
    val name: String,
    val description: String,
    val ownerId: Long,
    val parkingPricePerHour: Float,
    val activated: Boolean,
    val blocked: Boolean,
    val offered: Boolean,
    val longitude: Double,
    val latitude: Double,
    @ApiModelProperty(value = "Available periods of offering a parking space.")
    val availablePeriods: List<TimePeriod>,
    @ApiModelProperty(value = "Type of parking space sizes.",
            allowableValues = "SMALL, MEDIUM, LARGE" )
    val parkingSpaceSize: String
) {
    override fun toString(): String {
        return "CreateParkingSpaceCommand(name='$name', description='$description', " +
            "ownerId=$ownerId, parkingPricePerHour=$parkingPricePerHour, activated=$activated, " +
            "blocked=$blocked, offered=$offered, longitude=$longitude, latitude=$latitude, " +
            "availablePeriods=$availablePeriods, parkingSpaceSize='$parkingSpaceSize')"
    }
}


