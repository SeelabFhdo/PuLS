package de.fhdo.puls.environment_service.command.domian

import java.util.Date

class ReceivedDataSet(
        val sensorId: String,
        val receivedTimeStamp: Date,
        val particulate2: Float,
        val particulate10: Float,
        val light: Int,
        val temperature: Float,
        val humidity: Float
)