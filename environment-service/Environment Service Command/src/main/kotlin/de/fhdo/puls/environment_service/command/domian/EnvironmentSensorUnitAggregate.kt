package de.fhdo.puls.environment_service.command.domian

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document
class EnvironmentSensorUnitAggregate (

        @Id var id: String?,
        var name: String,
        var sensorBoxId: Long,
        var description: String,
        var status: SensorUnitStatus,
        var location: Location,
        var createdDate: Date,
        var lastModifiedDate: Date,
        var sensorType: SensorType
) {
    constructor(
            name: String,
            sensorBoxId: Long,
            description: String,
            status: SensorUnitStatus,
            location: Location,
            createdDate: Date,
            lastModifiedDate: Date,
            sensorType: SensorType
    ): this(null, name, sensorBoxId, description, status, location, createdDate,
        lastModifiedDate, sensorType)
}