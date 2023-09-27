package de.fhdo.puls.park_and_charge_service.command.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class ChargecloudParkingSpaceAggregate(
        @Id
        var id: String?,
        var name: String,

        /* The chargecloud-internal Id of the operator **/
        var operatorId: String,
        /* The name of the operator to be used in the UI **/
        var operatorName: String,

        /* The chargecloud-internal Id of the respective location **/
        var locationId: String,

        var location: Location,

        var address_street: String,
        var address_city: String,
        var address_zip: String,
        var address_country: String,

        var plugs: List<ChargecloudPlugInformation>,

        val createdDate: Date,
        var lastModifiedDate: Date

        ) {

        override fun toString(): String {
                return "ChargecloudParkingSpaceAggregate(id=$id, name='$name', operatorId='$operatorId', operatorName='$operatorName', locationId='$locationId', location=$location, address_street='$address_street', address_city='$address_city', address_zip='$address_zip', address_country='$address_country', plugs=$plugs, createdDate=$createdDate, lastModifiedDate=$lastModifiedDate)"
        }
}