package de.fhdo.seelab.puls.parkingspace_search_service.model;

import de.fhdo.seelab.puls.parkingspace_search_service.data.LocationData;
import de.fhdo.seelab.puls.parkingspace_search_service.events.ParkingSpaceDataReceivedEvent;
import de.fhdo.seelab.puls.parkingspace_search_service.services.LocationDataService;
import de.fhdo.seelab.puls.parkingspace_search_service.services.NominatimRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollector.class);

    private NominatimRequestService nominatimRequestService;
    private LocationDataService locationDataService;

    @Autowired
    public void setNominatimRequestService(NominatimRequestService nominatimRequestService) {
        this.nominatimRequestService = nominatimRequestService;
    }

    @Autowired
    public void setLocationDataService(LocationDataService locationDataService) {
        this.locationDataService = locationDataService;
    }

    @EventListener
    public void handleNewData(ParkingSpaceDataReceivedEvent event) {
        // 1.) get data as geo location
        LocationData newLocationData = event.getData();

        // 2.) enrich data by address information
        newLocationData.setAddress(
                nominatimRequestService.getAddressByGeoLocation(
                        newLocationData.getLocation().getY(),
                        newLocationData.getLocation().getX()
                )
        );

        // 3.) store the new location data
        locationDataService.save(newLocationData.toDto());
    }

}
