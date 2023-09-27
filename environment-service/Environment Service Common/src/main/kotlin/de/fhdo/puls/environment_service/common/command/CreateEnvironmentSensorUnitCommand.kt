package de.fhdo.puls.environment_service.common.command

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "CreateEnvironmentSensorUnitCommand",
        description = "Data structure for creating an EnvironmentSensorUnit, containing general "
        + "information regarding its location or SensorType.")
class CreateEnvironmentSensorUnitCommand (
        val name: String,
        @ApiModelProperty(value = "Transmitted id from CNI's sensor box, links to "
                + "EnvironmentInformation sensorBoxId property.")
        val sensorBoxId: Long,
        val description: String,
        @ApiModelProperty(value = "Status of the EnvironmentSensorUnit.",
                allowableValues = "ACTIVE, DEACTIVATED" )
        val status: String,
        val longitude: Float,
        val latitude: Float,
        @ApiModelProperty(value = "Type of the EnvironmentSensorUnitBox, PHYSICAL for existing " +
                "SensorUnits, VIRTUAL for simulated Sensor Units.",
                allowableValues = "PHYSICAL, VIRTUAL" )
        val sensorType: String
) {
        override fun toString(): String {
                return "CreateEnvironmentSensorUnitCommand(name='$name', " +
                        "sensorBoxId=$sensorBoxId, description='$description', status='$status', " +
                        "longitude=$longitude, latitude=$latitude, sensorType='$sensorType')"
        }
}