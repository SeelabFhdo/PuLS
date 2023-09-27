package de.fhdo.puls.park_and_charge_service.query.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.Date

@ApiModel(value = "ElectrifiedParkingSpaceCreatedEvent",
        description = "Data structure for receiving  electrified parking space information.")
class ParkingSpaceValueObject(
        val id: String,
        val originalId: String,
        val name: String,
        val description: String,
        val ownerId: Long,
        val parkingPricePerHour: Float,
        val createdDate: Date,
        val lastModifiedDate: Date,
        val activated: Boolean,
        val blocked: Boolean,
        val offered: Boolean,
        val longitude: Double,
        val latitude: Double,
        @ApiModelProperty(value = "Available periods of offering a parking space.")
        val availabilityPeriods: List<TimePeriod>,
        @ApiModelProperty(value = "Type of parking space sizes.",
                allowableValues = "SMALL, MEDIUM, LARGE" )
        val parkingSpaceSize: String
) {
    override fun toString(): String {
        return "ParkingSpaceValueObject(id='$id', originalId='$originalId', name='$name', " +
            "description='$description', ownerId=$ownerId, " +
            "parkingPricePerHour=$parkingPricePerHour, createdDate=$createdDate, " +
            "lastModifiedDate=$lastModifiedDate, activated=$activated, blocked=$blocked, " +
            "offered=$offered, longitude=$longitude, latitude=$latitude, " +
            "availabilityPeriods=$availabilityPeriods, parkingSpaceSize='$parkingSpaceSize')"
    }
}
