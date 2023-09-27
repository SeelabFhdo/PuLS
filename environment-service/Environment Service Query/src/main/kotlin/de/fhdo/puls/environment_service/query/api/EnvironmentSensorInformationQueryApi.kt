package de.fhdo.puls.environment_service.query.api

import de.fhdo.puls.environment_service.common.event.SensorValue
import de.fhdo.puls.environment_service.query.service.EnvironmentInformationService
import de.fhdo.puls.shared_library.security.RoleDeclaration
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/resources/v1", produces = ["application/json"])
@CrossOrigin(origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE])
class EnvironmentSensorInformationQueryApi {

    @Autowired
    lateinit var service: EnvironmentInformationService

    private val logger = KotlinLogging.logger {}

    @GetMapping("/environmentinformation/{sensorBoxId}/{sensor}/latest")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
            value = "Api endpoint for receiving the latest SensorValue for a sensor.",
            notes = "Note that specific path parameters are required. "
    )
    fun getLatestSensorValue(@ApiParam(value = "Id of CNI's SensorBoxUnit")
        @PathVariable sensorBoxId: Long,
        @ApiParam(value = "Possible values: humidity, temperature,  particulate2, particulate10, "
        + "light") @PathVariable sensor: String): SensorValue? {
        logger.info{ "Method getLatestSensorValue sensorBoxId: $sensorBoxId, sensor: $sensor " }
        return service.getLatestSensorValue(sensorBoxId, sensor, 1)?.first
    }

    @GetMapping("/environmentinformation/{sensorBoxId}/{sensor}/{numberOfValues}")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
            value = "Api endpoint for receiving a specific number of SensorValue for a sensor.",
            notes = "Note that specific path parameters are required. "
    )
    fun getSensorValue(@ApiParam(value = "Id of CNI's SensorBoxUnit")
        @PathVariable sensorBoxId: Long,
        @ApiParam(value = "Possible values: humidity, temperature,  particulate2, particulate10, "
        + "light") @PathVariable sensor: String,
        @ApiParam(value = "Number of received SensorValues.")
        @PathVariable numberOfValues: Int): List<SensorValue>? {
        logger.info{ "Method getSensorValue sensorBoxId: $sensorBoxId, sensor: $sensor, " +
            "values: $numberOfValues" }
        return service.getLatestSensorValue( sensorBoxId, sensor, numberOfValues)
    }

    @GetMapping("/environmentinformation/sensorboxid/{sensorBoxId}/measurementtypes")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
            value = "Api endpoint for receiving all possible measurement types for a sensor unit."
    )
    fun getSensorMeasurementTypes(
        @ApiParam(value = "Id of the sensor box.")
        @PathVariable sensorBoxId: Long): List<String> {
        return service.getMeasurementTypes()
    }
}