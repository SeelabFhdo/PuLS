package de.fhdo.puls.park_and_charge_service.command.api

import de.fhdo.puls.park_and_charge_service.command.service.ElectrifiedParkingSpaceService
import de.fhdo.puls.park_and_charge_service.common.command.CreateElectrifiedParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.command.DeleteElectrifiedParkingSpaceCommand
import de.fhdo.puls.park_and_charge_service.common.command.UpdateElectrifiedParkingSpaceCommand
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
class ElectrifiedParkingSpaceCommandApi {
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var electrifiedParkingSpaceService: ElectrifiedParkingSpaceService

    @PostMapping("/electrifiedparkingspace")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for creating a electrified parking space.",
            notes = "The property parkingSpaceSize requires a specific input."
    )
    fun createParkingSpace(@RequestBody @Valid command: CreateElectrifiedParkingSpaceCommand) {
        logger.info { "createParkingSpaceCommand received: $command.toString()" }
        electrifiedParkingSpaceService.handleCreateElectrifiedParkingSpaceCommand(command)
    }

    @PutMapping("/electrifiedparkingspace")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for updating a electrified parking space.",
            notes = "The property parkingSpaceSize requires a specific input."
    )
    fun changeParkingSpace(@RequestBody @Valid command: UpdateElectrifiedParkingSpaceCommand) {
        logger.info { "changeParkingSpaceCommand received: $command.toString()" }
        electrifiedParkingSpaceService.handleUpdateElectrifiedParkingSpaceCommand(command)
    }

    @DeleteMapping("/electrifiedparkingspace")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for deleting a electrified parking space.",
            notes = "The property parkingSpaceSize requires a specific input."
    )
    fun deleteParkingSpace(@RequestBody @Valid command: DeleteElectrifiedParkingSpaceCommand) {
        logger.info { "deleteParkingSpaceCommand received: $command.toString()" }
        electrifiedParkingSpaceService.handleDeleteElectrifiedParkingSpaceCommand(command)
    }
}
