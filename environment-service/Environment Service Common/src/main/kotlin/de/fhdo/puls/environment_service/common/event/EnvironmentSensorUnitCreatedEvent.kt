package de.fhdo.puls.environment_service.common.event

import java.util.Date

class EnvironmentSensorUnitCreatedEvent (
        val id: String,
        val name: String,
        val sensorBoxId: Long,
        val description: String,
        val status: String,
        val longitude: Float,
        val latitude: Float,
        val createdDate: Date,
        val lastModifiedDate: Date,
        val sensorTyp: String
) {
    override fun toString(): String {
        return "EnvironmentSensorUnitCreatedEvent(id='$id', name='$name', " +
                "sensorBoxId=$sensorBoxId, description='$description', status='$status', " +
                "longitude=$longitude, latitude=$latitude, createdDate=$createdDate, " +
                "lastModifiedDate=$lastModifiedDate, sensorTyp='$sensorTyp')"
    }
}