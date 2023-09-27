package de.fhdo.puls.park_and_charge_service.query.api

import de.fhdo.puls.park_and_charge_service.common.event.ChargingStatus
import de.fhdo.puls.park_and_charge_service.query.domain.ChargingInformationData
import de.fhdo.puls.park_and_charge_service.query.domain.KeepAliveInformation
import de.fhdo.puls.park_and_charge_service.query.domain.ParkingStatusInformation
import de.fhdo.puls.park_and_charge_service.query.domain.SensorValue
import de.fhdo.puls.park_and_charge_service.query.service.ElectrifiedParkingSpaceService
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/resources/v1", produces = ["application/json"])
@CrossOrigin(
    origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE]
)
class ChargingInformationQueryApi {

    @Autowired
    lateinit var service: ElectrifiedParkingSpaceService

    private val logger = KotlinLogging.logger {}

    @GetMapping("/charginginformation/{chargingStationId}/{infoType}/latest")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving the latest status information from a sensor."
    )
    fun getLatestSensorValue(@PathVariable chargingStationId: String, @PathVariable infoType: String): SensorValue {
        logger.info { "Method getLatestSensorValue chargingStationId: $chargingStationId, sensor: $infoType " }
        return service.getChargingStationInformation(chargingStationId, infoType).last()
    }

    @GetMapping("/charginginformation/{chargingStationId}/{infoType}/all")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving the all status information from a sensor."
    )
    fun getAllSensorValue(@PathVariable chargingStationId: String, @PathVariable infoType: String): List<SensorValue> {
        logger.info { "Method getAllSensorValue chargingStationId: $chargingStationId, sensor: $infoType " }
        return service.getChargingStationInformation(chargingStationId, infoType)
    }

    @GetMapping("/charginginformation/{chargingStationId}/status/latest")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving the latest charging status information."
    )
    fun getLatestStatusInformation(@PathVariable chargingStationId: String): ChargingInformationData {
        logger.info { "Method getLatestStatusInformation chargingStationId: $chargingStationId" }
        return service.getStatusInformationData(chargingStationId).last()
    }

    @GetMapping("/charginginformation/{chargingStationId}/status/all")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving all charging status information."
    )
    fun getAllStatusInformation(@PathVariable chargingStationId: String): List<ChargingInformationData> {
        logger.info { "Method getAllStatusInformation chargingStationId: $chargingStationId" }
        return service.getStatusInformationData(chargingStationId)
    }

    @GetMapping("/charginginformation/{chargingStationId}/keepalive/latest")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving the latest keepalive data."
    )
    fun getKeepAliveInformation(@PathVariable chargingStationId: String): KeepAliveInformation {
        logger.info { "Method getKeepAliveInformation chargingStationId: $chargingStationId" }
        return service.getKeepAliveInformation(chargingStationId).last()
    }

    @GetMapping("/charginginformation/{chargingStationId}/status/current")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving the current status of a charging station."
    )
    fun getCurrentStatus(@PathVariable chargingStationId: String): ChargingStatus {
        logger.info { "Method getCurrentStatus chargingStationId: $chargingStationId" }
        return service.getCurrentChargingStationStatus(chargingStationId)
    }

    @GetMapping("/charginginformation/{chargingStationId}/parking/latest")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving the latest parking status"
    )
    fun getLatestParkingInformation(@PathVariable chargingStationId: String): ParkingStatusInformation {
        logger.info { "Method getLatestParkingInformation chargingStationId: $chargingStationId" }
        return service.getParkingInformation(chargingStationId).last()
    }

    @GetMapping("/charginginformation/{chargingStationId}/parking/all")
    @Throws(Exception::class)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
        value = "Api endpoint for receiving the all parking status"
    )
    fun getAllParkingInformation(@PathVariable chargingStationId: String): List<ParkingStatusInformation> {
        logger.info { "Method getAllParkingInformation chargingStationId: $chargingStationId" }
        return service.getParkingInformation(chargingStationId)
    }
}