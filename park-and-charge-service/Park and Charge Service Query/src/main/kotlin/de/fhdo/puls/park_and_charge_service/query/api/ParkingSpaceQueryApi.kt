package de.fhdo.puls.park_and_charge_service.query.api

import de.fhdo.puls.park_and_charge_service.query.domain.ParkingSpaceValueObject
import de.fhdo.puls.park_and_charge_service.query.repository.ParkingSpaceRepository
import de.fhdo.puls.park_and_charge_service.query.service.ParkingSpaceService
import io.swagger.annotations.Api
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
@Api(description = "This api is deprecated, please use the EnvironmentSensorInformationQueryApi")
@CrossOrigin(origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE])
class ParkingSpaceQueryApi {
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var parkingSpaceRepository: ParkingSpaceRepository

    @Autowired
    lateinit var parkingSpaceService: ParkingSpaceService

    @GetMapping("/parkingspaces")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving all parking spaces."
    )
    fun getAllParkingSpaces(): List<ParkingSpaceValueObject> {
        logger.info { "Method:" }
        val parkingSpaces = parkingSpaceRepository.findAll().toList()
        return parkingSpaces.map { parkingSpaceService.valueObjectFromParkingSpaceAggregate(it) }
    }

    @GetMapping("/parkingspace/id/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving a parking spaces by id."
    )
    fun getParkingSpaceById(
            @PathVariable @ApiParam(value = "Id of the parking space") id: String):
            ParkingSpaceValueObject {
        logger.info { "Method: getParkingSpaceById id: $id" }
        return parkingSpaceService.valueObjectFromParkingSpaceAggregate(parkingSpaceRepository
                .findById(id).orElse(null))
    }

    @PostMapping("/parkingspace/ids")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving multiple parking spaces their ids."
    )
    fun getParkingSpacesByIds(
            @RequestBody @ApiParam(value = "Ids of the parking spaces") ids: List<String>):
            List<ParkingSpaceValueObject> {
        logger.info { "Method: getParkingSpaceById id: ${ids.toString()}" }
        return parkingSpaceService.findParkingSpacesByIds(ids)
    }

    @PostMapping("/parkingspace/originalids")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
        value = "Api endpoint for receiving multiple parking spaces their original ids."
    )
    fun getParkingSpacesByOriginalIds(
        @RequestBody @ApiParam(value = "Original ids of the parking spaces") ids: List<String>):
            List<ParkingSpaceValueObject> {
        logger.info { "Method: getParkingSpaceByOriginalId id: ${ids.toString()}" }
        return parkingSpaceService.findParkingSpacesByOriginalIds(ids)
    }
}