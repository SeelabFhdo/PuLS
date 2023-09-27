package de.fhdo.puls.environment_service.common.event

import java.util.Date

class EnvironmentSensorUnitUpdatedEvent (
        val id: String,
        val name: String,
        val description: String,
        val status: String,
        val longitude: Float,
        val latitude: Float,
        val lastModifiedDate: Date,
        val sensorTyp: String

) {
    override fun toString(): String {
        return "EnvironmentSensorUnitUpdatedEvent(id='$id', name='$name', " +
            "description='$description', status='$status', longitude=$longitude, " +
            "latitude=$latitude, lastModifiedDate=$lastModifiedDate, sensorTyp='$sensorTyp')"
    }
}