package de.fhdo.puls.park_and_charge_service.command.domain

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChargecloudPlugInformation

        if (plugType != other.plugType) return false
        if (plugMaxPower != other.plugMaxPower) return false
        if (plugPowerType != other.plugPowerType) return false
        if (roaming != other.roaming) return false

        return true
    }

    override fun hashCode(): Int {
        var result = plugType.hashCode()
        result = 31 * result + plugMaxPower.hashCode()
        result = 31 * result + plugPowerType.hashCode()
        result = 31 * result + roaming.hashCode()
        return result
    }
}