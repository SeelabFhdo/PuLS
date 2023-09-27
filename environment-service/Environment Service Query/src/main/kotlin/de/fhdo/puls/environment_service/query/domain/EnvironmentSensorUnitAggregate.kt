package de.fhdo.puls.environment_service.query.domain

import org.springframework.data.annotation.Id

class EnvironmentSensorUnitAggregate (
        @Id var id: String?,
        var originalId: String,
        var sensorBoxId: Long,
        var name: String,
        var description: String,
        var status: SensorUnitStatus,
        var location: Location,
        var sensorType: SensorType
) {
    constructor(
            name: String,
            originalId: String,
            sensorBoxId: Long,
            description: String,
            status: SensorUnitStatus,
            location: Location,
            sensorType: SensorType
    ): this(null, originalId, sensorBoxId, name, description, status, location, sensorType)
}