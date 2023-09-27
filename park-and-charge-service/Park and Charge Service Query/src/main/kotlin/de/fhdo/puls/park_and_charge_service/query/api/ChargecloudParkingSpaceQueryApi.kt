package de.fhdo.puls.park_and_charge_service.query.api

import de.fhdo.puls.park_and_charge_service.query.domain.ChargecloudParkingSpaceValueObject
import de.fhdo.puls.park_and_charge_service.query.service.ChargecloudService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/resources/v1")
@CrossOrigin(origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE])
class ChargecloudParkingSpaceQueryApi {
    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var chargecloudService: ChargecloudService

    @GetMapping("/chargecloudparkingspaces")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving all chargecloud parking spaces."
    )
    fun getAllChargecloudParkingSpaces(): List<ChargecloudParkingSpaceValueObject> {
        logger.info { "Method: getAllChargecloudParkingSpaces" }
        return chargecloudService.getAllChargecloudParkingSpaces()
    }

    @GetMapping("/chargecloudparkingspace/id/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving a chargecloud parking space by id."
    )
    fun getChargecloudParkingSpaceById(
            @PathVariable @ApiParam(value = "Id of the parking space") id: String):
            ChargecloudParkingSpaceValueObject {
        logger.info { "Method: getChargecloudParkingSpaceById id: $id" }
        return chargecloudService.getChargecloudParkingSpaceById(id)
    }
}