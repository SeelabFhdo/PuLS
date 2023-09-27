package de.fhdo.puls.environment_service.common.event

class EnvironmentDataReceivedEvent (
        val sensorUnitId: String,
        val particulates2: SensorValue,
        val particulates10: SensorValue,
        val light: SensorValue,
        val temperature: SensorValue,
        val humidity: SensorValue
) {
    override fun toString(): String {
        return "EnvironmentDataReceivedEvent(sensorUnitId='$sensorUnitId', " +
            "particulates2=$particulates2, particulates10=$particulates10, " +
            "light=$light, temperature=$temperature, humidity=$humidity)"
    }
}