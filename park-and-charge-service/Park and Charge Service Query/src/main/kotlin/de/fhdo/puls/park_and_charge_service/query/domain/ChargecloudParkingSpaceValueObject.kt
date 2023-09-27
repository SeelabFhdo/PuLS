package de.fhdo.puls.park_and_charge_service.query.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document
class ChargecloudParkingSpaceValueObject(
        var id: String,
        /** The id of this entity in the db of the Command-Microservice */
        var originalId: String,

        var name: String,

        /* The chargecloud-internal Id of the operator **/
        var operatorId: String,
        /* The name of the operator to be used in the UI **/
        var operatorName: String,

        /* The chargecloud-internal Id of the respective location **/
        var locationId: String,

        var latitude: Double,
        var longitude: Double,

        var address_street: String,
        var address_city: String,
        var address_zip: String,
        var address_country: String,

        var plugs: List<ChargecloudPlugInformation>,

        var createdDate: Date,
        var lastModifiedDate: Date
)