package de.fhdo.puls.park_and_charge_service.query.domain

class ChargecloudPlugInformation(
        /** Plug-Type (e.g. Type1, Type2, CHAdeMO */
        var plugType: String,
        /** Max Power in kW */
        var plugMaxPower: String, // max kW
        /** AC or DC */
        var plugPowerType: String,
        /** Roaming support */
        var roaming: Boolean
) {
    override fun toString(): String {
        return "ChargecloudPlugInformation(plugType='$plugType', plugMaxPower='$plugMaxPower', plugPowerType='$plugPowerType', roaming=$roaming)"
    }
}