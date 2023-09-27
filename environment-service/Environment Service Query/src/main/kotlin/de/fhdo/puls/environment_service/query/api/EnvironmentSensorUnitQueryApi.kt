package de.fhdo.puls.environment_service.query.api

import de.fhdo.puls.environment_service.query.domain.EnvironmentSensorUnitValueObject
import de.fhdo.puls.environment_service.query.repository.EnvironmentSensorUnitRepository
import de.fhdo.puls.environment_service.query.service.EnvironmentSensorUnitService
import de.fhdo.puls.shared_library.security.RoleDeclaration.ROLE_USER
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
@RequestMapping("/resources/v1")
@CrossOrigin(origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE])
class EnvironmentSensorUnitQueryApi {
    @Autowired
    lateinit var unitRepository: EnvironmentSensorUnitRepository

    @Autowired
    lateinit var unitService: EnvironmentSensorUnitService

    private val logger = KotlinLogging.logger {}

    @GetMapping("/environmentsensorunit/name/{name}")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving general information for a specific SensorUnit.")
    fun getEnvironmentSensorUnitByName(@ApiParam(value = "Name of the EnvironmentSensorBoxUnit" )
        @PathVariable name: String): EnvironmentSensorUnitValueObject {
        val aggregate = unitRepository.findEnvironmentSensorUnitAggregateByName(name)
        logger.info{ "Method getEnvironmentSensorUnitByName for: $name" }
        return unitService.valueObjectFromAggregate(aggregate)
    }

    @GetMapping("/environmentsensorunit/sensorboxid/{sensorBoxId}")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
            value = "Api endpoint for receiving general information for a specific SensorUnit.")
    fun getEnvironmentSensorUnitBySensorBoxId(
        @ApiParam(value = "SensorBoxId of the EnvironmentSensorBoxUnit" )
        @PathVariable sensorBoxId: Long): EnvironmentSensorUnitValueObject {
        val aggregate = unitRepository.findEnvironmentSensorUnitAggregateBySensorBoxId(sensorBoxId)
        logger.info{ "Method getEnvironmentSensorUnitBySensorBoxId for: $sensorBoxId" }
        return unitService.valueObjectFromAggregate(aggregate)
    }

    @GetMapping("/environmentsensorunits")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Api endpoint for receiving general information for all SensorUnit.")
    fun getEnvironmentSensorUnits(): List<EnvironmentSensorUnitValueObject> {
        logger.info{ "Method getEnvironmentSensorUnits." }
        return unitService.valueObjectListFromAggregates(unitRepository.findAll().toList())
    }
}