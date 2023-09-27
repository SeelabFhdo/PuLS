package de.fhdo.puls.environment_service.query.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "EnvironmentSensorUnitValueObject",
        description = "ValueObject for receiving EnvironmentSensorUnit information.")
class EnvironmentSensorUnitValueObject (
        var name: String,
        @ApiModelProperty(value = "Transmitted id from CNI's sensor box, links to "
                + "EnvironmentInformation sensorBoxId property.")
        var sensorBoxId: Long,
        var description: String,
        @ApiModelProperty(value = "Status of the EnvironmentSensorUnit.",
                allowableValues = "ACTIVE, DEACTIVATED" )
        var status: String,
        var longitude: Float,
        var latitude: Float,
        @ApiModelProperty(value = "Type of the EnvironmentSensorUnitBox, PHYSICAL for existing " +
                "SensorUnits, VIRTUAL for simulated Sensor Units.",
                allowableValues = "PHYSICAL, VIRTUAL" )
        var sensorType: String
)