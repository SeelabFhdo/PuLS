package de.fhdo.puls.park_and_charge_service.command.api

import de.fhdo.puls.park_and_charge_service.command.service.ParkingSpaceService
import de.fhdo.puls.park_and_charge_service.common.command.CreateParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.command.UpdateParkingSpaceCommand
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/resources/v1")
@CrossOrigin(origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE])
class ParkingSpaceCommandApi {
    @Autowired
    lateinit var parkingSpaceService: ParkingSpaceService
    private val logger = KotlinLogging.logger {}

    @PostMapping("/parkingspace")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for creating a parking space.",
            notes = "The property parkingSpaceSize requires a specific input."
    )
    fun createParkingSpace(@RequestBody @Valid command: CreateParkingSpaceCommand) {
        logger.info { "createParkingSpaceCommand received: $command.toString()" }
        parkingSpaceService.handleParkingSpaceCreatedCommand(command)
    }

    @PutMapping("/parkingspace")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for changing a parking space.",
            notes = "The property parkingSpaceSize requires a specific input."
    )
    fun changeParkingSpace(@RequestBody @Valid command: UpdateParkingSpaceCommand) {
        logger.info { "changeParkingSpaceCommand received: $command.toString()" }
        parkingSpaceService.handleParkingSpaceUpdatedCommand(command);
    }

    /*
    @DeleteMapping("/parkingspace/id")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for deleting a parking space."
    )
    fun deleteParkingSpace(@RequestBody @Valid command: CreateParkingSpaceCommand) {
        logger.info { "deleteParkingSpaceCommand received: $command.toString()" }
        parkingSpaceService.handleParkingSpaceDeletedCommand(command);
    }
    */
}
