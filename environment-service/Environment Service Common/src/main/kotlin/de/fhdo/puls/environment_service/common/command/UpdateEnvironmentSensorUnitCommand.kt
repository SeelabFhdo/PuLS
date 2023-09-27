package de.fhdo.puls.environment_service.common.command

class UpdateEnvironmentSensorUnitCommand (
        val id: String,
        val name: String,
        val description: String,
        val status: String,
        val longitude: Float,
        val latitude: Float
)