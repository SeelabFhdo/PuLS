package de.fhdo.puls.environment_service.query.api

import de.fhdo.puls.environment_service.common.event.SensorValue
import de.fhdo.puls.environment_service.query.domain.EnvironmentInformationValueObject
import de.fhdo.puls.environment_service.query.repository.EnvironmentInformationRepository
import de.fhdo.puls.environment_service.query.service.EnvironmentSensorUnitService
import de.fhdo.puls.shared_library.security.RoleDeclaration.ROLE_USER
import io.swagger.annotations.Api
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
@Api(description = "This api is deprecated, please use the EnvironmentSensorInformationQueryApi")
@CrossOrigin(origins = ["\${cors.origins}"], allowedHeaders = ["*"], methods = [RequestMethod.HEAD,
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE])
class EnvironmentInformationQueryApi {
    @Autowired
    lateinit var environmentInformationRepository: EnvironmentInformationRepository

    @Autowired
    lateinit var environmentSensorUnitService: EnvironmentSensorUnitService

    private val logger = KotlinLogging.logger {}

    @GetMapping("/environmentinformation/{id}")
    @PreAuthorize("hasRole('USER')")
    fun getEnvironmentInformation(@PathVariable id: Long): EnvironmentInformationValueObject {
        logger.info { "Method: getEnvironmentInformation with id: $id called." }
        val information =
            environmentInformationRepository.findEnvironmentInformationBySensorBoxId(id)
        return environmentSensorUnitService.valueObjectFromEnvironmentInformation(information)
    }

    @GetMapping("/environmentinformation/{id}/particulate2")
    @PreAuthorize("hasRole('USER')")
    fun getParticulate2Information(@PathVariable id: Long): List<SensorValue> {
        logger.info { "Method: getParticulate2Information with id: $id called." }
        return environmentInformationRepository.findEnvironmentInformationBySensorBoxId(id)
            .particulate2Data
    }

    @GetMapping("/environmentinformation/{id}/particulate10")
    @PreAuthorize("hasRole('USER')")
    fun getParticulate10Information(@PathVariable id: Long): List<SensorValue> {
        logger.info { "Method: getParticulate10Information with id: $id called." }
        return environmentInformationRepository.findEnvironmentInformationBySensorBoxId(id)
                .particulate10Data
    }

    @GetMapping("/environmentinformation/{id}/light")
    @PreAuthorize("hasRole('USER')")
    fun getLightInformation(@PathVariable id: Long): List<SensorValue> {
        logger.info { "Method: getLightInformation with id: $id called." }
        return environmentInformationRepository.findEnvironmentInformationBySensorBoxId(id)
                .lightData
    }

    @GetMapping("/environmentinformation/{id}/temperature")
    @PreAuthorize("hasRole('USER')")
    fun getTemperatureInformation(@PathVariable id: Long): List<SensorValue> {
        logger.info { "Method: getTemperatureInformation with id: $id called." }
        return environmentInformationRepository.findEnvironmentInformationBySensorBoxId(id)
                .temperatureData
    }

    @GetMapping("/environmentinformation/{id}/humidity")
    @PreAuthorize("hasRole('USER')")
    fun getHumidityInformation(@PathVariable id: Long): List<SensorValue> {
        logger.info { "Method: getHumidityInformation with id: $id called." }
        return environmentInformationRepository.findEnvironmentInformationBySensorBoxId(id)
                .humidityData
    }
}