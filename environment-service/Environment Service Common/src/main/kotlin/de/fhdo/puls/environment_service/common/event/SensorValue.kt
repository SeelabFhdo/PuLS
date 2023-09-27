package de.fhdo.puls.environment_service.common.event

import io.swagger.annotations.ApiModel
import java.util.Date

@ApiModel(value = "SensorValue",
    description = "Data structure for storing a sensor value. Note: The timestamp is set by the "
    + "by the EnvironmentServiceCommand.")
class SensorValue(
        val value: String,
        val timestamp: Date
)