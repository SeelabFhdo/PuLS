package de.fhdo.puls.environment_service.query.domain

import de.fhdo.puls.environment_service.common.event.SensorValue
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.LinkedList

@Document
class EnvironmentInformation (
        @Id var id: String?,
        var sensorBoxId: Long
) {
    var particulate2Data: LinkedList<SensorValue> = LinkedList()
    var particulate10Data: LinkedList<SensorValue> = LinkedList()
    var lightData: LinkedList<SensorValue> = LinkedList()
    var temperatureData: LinkedList<SensorValue> = LinkedList()
    var humidityData: LinkedList<SensorValue> = LinkedList()

    constructor(sensorBoxId: Long): this(null, sensorBoxId)
}