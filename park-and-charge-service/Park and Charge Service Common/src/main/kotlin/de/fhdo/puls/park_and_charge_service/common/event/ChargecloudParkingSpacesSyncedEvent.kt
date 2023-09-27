package de.fhdo.puls.park_and_charge_service.common.command

class ChargecloudParkingSpacesSyncedEvent(
        var parkingSpaces: MutableList<ChargecloudParkingSpace> = mutableListOf()
) {
        override fun toString(): String {
                return "ChargecloudParkingSpacesSyncedEvent(parkingSpaces=List[${parkingSpaces.size}])"
        }
}

class ChargecloudParkingSpace (
        var name: String,

        /** The id as used in the command-microservice" */
        var commandId: String,

        /** Chargecloud-internal id of the operator */
        var operatorId: String,
        /** Name of the operator to be used in the UI */
        var operatorName: String,

        /** Chargecloud-internal Id of the respective location */
        var locationId: String,

        var latitude: Double,
        var longitude: Double,

        var address_street: String,
        var address_city: String,
        var address_zip: String,
        var address_country: String,

        var plugs: List<ChargecloudPlugInformation>
) {
        override fun toString(): String {
                return "ChargecloudParkingSpace(name='$name')"
        }
}

class ChargecloudPlugInformation (
        /** Plug-Type (e.g. Type1, Type2, CHAdeMO */
        var plugType: String,
        /** Max provided power in kW */
        var plugMaxPower: String,
        /** AC or DC */
        var plugPowerType: String,
        /** Roaming support */
        var roaming: Boolean
)