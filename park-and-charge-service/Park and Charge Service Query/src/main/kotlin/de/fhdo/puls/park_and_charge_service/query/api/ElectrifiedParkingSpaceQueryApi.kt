package de.fhdo.puls.park_and_charge_service.query.api

import de.fhdo.puls.park_and_charge_service.query.domain.ElectrifiedParkingSpaceValueObject
import de.fhdo.puls.park_and_charge_service.query.repository.ElectrifiedParkingSpaceRepository
import de.fhdo.puls.park_and_charge_service.query.service.ElectrifiedParkingSpaceService
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
class ElectrifiedParkingSpaceQueryApi {
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var electrifiedParkingSpaceService: ElectrifiedParkingSpaceService

    @Autowired
    lateinit var electrifiedParkingSpaceRepository: ElectrifiedParkingSpaceRepository

    @GetMapping("/electrifiedparkingspaces")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving all parking spaces."
    )
    fun getAllElectrifiedParkingSpaces(): List<ElectrifiedParkingSpaceValueObject> {
        logger.info { "Method: getAllParkingSpaces" }
        val parkingSpaces = electrifiedParkingSpaceRepository.findAll().toList()
        return parkingSpaces.map {
            electrifiedParkingSpaceService.valueObjectFromElectrifiedParkingSpaceAggregate(it)
        }
    }

    @GetMapping("/electrifiedparkingspace/id/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving a parking spaces by id."
    )
    fun getElectrifiedParkingSpaceById(
            @PathVariable @ApiParam(value = "Id of the parking space") id: String):
            ElectrifiedParkingSpaceValueObject {
        logger.info { "Method: getParkingSpaceById id: $id" }
        return electrifiedParkingSpaceService.valueObjectFromElectrifiedParkingSpaceAggregate(
                electrifiedParkingSpaceRepository.findById(id).orElse(null))
    }

    @PostMapping("/electrifiedparkingspace/ids")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for receiving multiple parking spaces by their ids."
    )
    fun getElectrifiedParkingSpacesByIds(
            @RequestBody @ApiParam(value = "Id of the parking space") ids: List<String>):
            List<ElectrifiedParkingSpaceValueObject> {
        logger.info { "Method: getElectrifiedParkingSpacesByIds ids: ${ids.toString()}" }
        return electrifiedParkingSpaceService.findElectrifiedParkingSpacesByIds(ids)
    }

    @PostMapping("/electrifiedparkingspace/originalids")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
        value = "Api endpoint for receiving multiple parking spaces by their original ids."
    )
    fun getElectrifiedParkingSpacesByOriginalIds(
        @RequestBody @ApiParam(value = "Id of the parking space") ids: List<String>):
            List<ElectrifiedParkingSpaceValueObject> {
        logger.info { "Method: getElectrifiedParkingSpacesByIds ids: ${ids.toString()}" }
        return electrifiedParkingSpaceService.findElectrifiedParkingSpacesByOriginalIds(ids)
    }
}