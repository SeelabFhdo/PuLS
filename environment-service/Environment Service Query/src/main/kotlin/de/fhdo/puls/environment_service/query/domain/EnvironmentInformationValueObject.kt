package de.fhdo.puls.environment_service.query.domain

import de.fhdo.puls.environment_service.common.event.SensorValue
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.LinkedList

@ApiModel(value = "EnvironmentInformationValueObject",
        description = "ValueObject for receiving stored sensor information.")
class EnvironmentInformationValueObject(
        @ApiModelProperty(value = "Transmitted id from CNI's sensor box, links to "
                + "EnvironmentInformation sensorBoxId property.")
        val sensorBoxId: String,
        val particulate2Data: LinkedList<SensorValue> = LinkedList(),
        val particulate10Data: LinkedList<SensorValue> = LinkedList(),
        val lightData: LinkedList<SensorValue> = LinkedList(),
        val temperatureData: LinkedList<SensorValue> = LinkedList(),
        val humidityData: LinkedList<SensorValue> = LinkedList()
)