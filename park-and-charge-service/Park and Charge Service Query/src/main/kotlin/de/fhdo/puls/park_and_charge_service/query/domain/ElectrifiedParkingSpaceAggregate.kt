package de.fhdo.puls.park_and_charge_service.query.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date
@Document
class ElectrifiedParkingSpaceAggregate(
        @Id
        var id: String?,
        val chargingStationId: String,
        val originalId: String,
        var name: String,
        var description: String,
        var ownerId: Long,
        var parkingPricePerHour: Float,
        val createdDate: Date,
        var lastModifiedDate: Date,
        var activated: Boolean,
        var blocked: Boolean,
        var offered: Boolean,
        var location: Location,
        var availabilityPeriods: List<TimePeriod>,
        var parkingSpaceSize: ParkingSpaceSize,
        var chargingPricePerKWH: Float,
        var chargingType: ChargingType,
        var pluginType: String,
        var parkingSenorId: String
) {
        override fun toString(): String {
                return "ElectrifiedParkingSpaceAggregate(id=$id, chargingStationId='$chargingStationId', " +
                        "originalId='$originalId', name='$name', description='$description', ownerId=$ownerId, " +
                        "parkingPricePerHour=$parkingPricePerHour, createdDate=$createdDate, " +
                        "lastModifiedDate=$lastModifiedDate, activated=$activated, blocked=$blocked, " +
                        "offered=$offered, location=$location, availabilityPeriods=$availabilityPeriods," +
                        " parkingSpaceSize=$parkingSpaceSize, chargingPricePerKWH=$chargingPricePerKWH," +
                        " chargingType=$chargingType, pluginType='$pluginType', parkingSenorId='$parkingSenorId')"
        }
}