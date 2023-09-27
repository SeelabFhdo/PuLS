package de.fhdo.puls.environment_service.command.api

import de.fhdo.puls.environment_service.command.service.EnvironmentSensorUnitService
import de.fhdo.puls.environment_service.common.command.CreateEnvironmentSensorUnitCommand
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/resources/v1")
@CrossOrigin(origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE])
class EnvironmentSensorUnitCommandApi {
    @Autowired
    lateinit var sensorUnitService: EnvironmentSensorUnitService

    private val logger = KotlinLogging.logger {}

    @PostMapping("/environmentsensorunit")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for creating an EnvironmentSensorUnit.",
            notes = "The properties sensorType and status require a specific input."
    )
    fun createEnvironmentSensorUnit(@RequestBody @Valid
                                    command: CreateEnvironmentSensorUnitCommand) {
        logger.info { "CreateEnvironmentSensorUnitCommand received: $command.toString()" }
        sensorUnitService.handleCreateEnvironmentSensorUnitCommand(command)
    }
}