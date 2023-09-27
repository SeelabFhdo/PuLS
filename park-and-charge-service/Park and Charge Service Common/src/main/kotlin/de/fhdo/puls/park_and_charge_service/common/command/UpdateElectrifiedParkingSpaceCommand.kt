package de.fhdo.puls.park_and_charge_service.common.command

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "ElectrifiedParkingSpaceUpdatedEvent",
        description = "Data structure for updating an electrified parking space, containing general"
                + " information regarding its location or pricing.")
class UpdateElectrifiedParkingSpaceCommand (
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
        val chargingPricePerKWH: Float,
        @ApiModelProperty(value = "Type of the charging type.",
                allowableValues = "NORMAL, FAST" )
        val chargingType: String,
        val pluginType: String,
        @ApiModelProperty(value = "Available periods of offering a parking space.")
        val availablePeriods: List<TimePeriod>,
        @ApiModelProperty(value = "Type of parking space sizes.",
                allowableValues = "SMALL, MEDIUM, LARGE" )
        val parkingSpaceSize: String,
        val parkingSensorId: String?
)
