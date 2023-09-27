package de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud

import de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilityLocationsData.EmobilityLocationsDataDTO
import de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilityLocationsData.Data
import de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilitylocationsDataDetails.EmobilityLocationsDataDetailsDTO
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class ChargecloudClient {
    private val logger = KotlinLogging.logger {}

    @Value(value = "\${chargecloud.backend.api.url}")
    lateinit var apiUrl: String

    @Value(value = "\${chargecloud.backend.api.client-secret}")
    lateinit var clientSecret: String

    fun getEmobilityLocationsData() : List<Data> {
        // Umkreis Technologiepark (5 Stationen)
        // val swlat = "51.48028771603471";
        // val swlon = "7.396512154775361";
        // val nelat = "51.503133562277945";
        // val nelon = "7.426595811086396";

        // Ganz Dortmund
        val swlat = "51.416944";
        val swlon = "7.303333";
        val nelat = "51.601389";
        val nelon = "7.638889";

        val url = "$apiUrl/rest:contract/$clientSecret/getEmobilityLocationsData" +
                "?swlat=${swlat}" +
                "&swlng=${swlon}" +
                "&nelat=${nelat}" +
                "&nelng=${nelon}"

        val restTemplate = RestTemplate()
        val headers = HttpHeaders();
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON);
        val entity = HttpEntity<String>(headers);

        val response = restTemplate.exchange(url, HttpMethod.GET, entity, EmobilityLocationsDataDTO::class.java);

        if (response.body?.errorCode.toString().isNullOrEmpty()) {
            logger.info { "Calling the chargecloud api (${url}) failed with error code ${response.body?.errorCode}" }
            throw Exception("Calling the chargecloud api (${url}) failed with error code ${response.body?.errorCode}")
        }

        // TODO So korrekt? "?:"
        return response.body?.data ?: Collections.emptyList();
    }

    fun getEmobilityLocationsDataDetails(locationId: String) : EmobilityLocationsDataDetailsDTO? {
        val url = "$apiUrl/rest:contract/$clientSecret/getEmobilityLocationsDataDetails" +
                "?offset=0" +
                "&limit=100" +
                "&locationId=${locationId}"

        val restTemplate = RestTemplate()
        val headers = HttpHeaders();
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON);
        val entity = HttpEntity<String>(headers);

        val response = restTemplate.exchange(url, HttpMethod.GET, entity, EmobilityLocationsDataDetailsDTO::class.java);

        if (response.body?.errorCode.toString().isNullOrEmpty()) {
            logger.info { "Calling the chargecloud api (${url}) failed with error code ${response.body?.errorCode}" }
            throw Exception("Calling the chargecloud api (${url}) failed with error code ${response.body?.errorCode}")
        }

        return response.body
    }
}