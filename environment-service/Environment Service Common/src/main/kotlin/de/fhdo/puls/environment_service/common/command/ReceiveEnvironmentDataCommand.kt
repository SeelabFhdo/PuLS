package de.fhdo.puls.environment_service.common.command

class ReceiveEnvironmentDataCommand (
        val sensorUnitId: String,
        val particulate2: Float,
        val particulate10: Float,
        val light: Int,
        val temperature: Float,
        val humidity: Float,
        val communicationType: Int,
        val signalPower: Int,
        val signalNoiseRang: Float,
        val spreadingFaktorDownlink: Int,
        val spreadingFaktorUplink: Int
)