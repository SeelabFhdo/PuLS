package de.fhdo.puls.park_and_charge_service.command.api

import de.fhdo.puls.park_and_charge_service.command.service.ChargecloudService
import io.swagger.annotations.ApiOperation
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
class ChargecloudParkingSpaceCommandApi {
    @Autowired
    lateinit var chargecloudService: ChargecloudService
    private val logger = KotlinLogging.logger {}

    @PostMapping("/sync/chargecloud")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiResponses(ApiResponse(code = 400, message = "Invalid request"))
    @Throws(Exception::class)
    @ApiOperation(
            value = "Api endpoint for importing/syncing charging stations from the Chargecloud API.",
            notes = "Only charging stations for Dortmund are synced."
    )
    fun syncChargeCloud() {
        logger.info { "syncChargeCloudCommand received" }
        chargecloudService.handleSyncCommand();
    }
}
