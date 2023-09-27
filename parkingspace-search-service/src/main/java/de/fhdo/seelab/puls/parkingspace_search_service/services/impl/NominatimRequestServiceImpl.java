/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhdo.seelab.puls.parkingspace_search_service.services.NominatimRequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service(value = "nominatimRequestService")
public class NominatimRequestServiceImpl implements NominatimRequestService {

    @Value(value = "${nominatim.apiUrl}")
    private String apiUrl;

    @Override
    public String getAddressByGeoLocation(double lat, double lng) {
        String result = "";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(
                apiUrl + "/reverse?format=geojson&lat={lat}&lon={lng}",
                String.class,
                String.valueOf(lat),
                String.valueOf(lng)
        );

        try {
                ObjectMapper mapper = new ObjectMapper();
                if (response.getBody() != null) {
                    JsonNode root = mapper.readTree(response.getBody());
                    result = root.findValue("display_name").asText();
                }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // wait a second to debounce further requests in the queue
        // (if the service was offline and restarts again it gets all pending
        // messages from the kafka broker. This may result in too many
        // external requests against the nominatim backend service which
        // results in a "429 Too Many Requests" exception.
        // To prevent this behaviour we debounce the request by a one
        // second delay between each new request)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
