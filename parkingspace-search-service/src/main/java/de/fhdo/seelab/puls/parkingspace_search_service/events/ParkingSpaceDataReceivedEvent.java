package de.fhdo.seelab.puls.parkingspace_search_service.events;

import de.fhdo.seelab.puls.parkingspace_search_service.data.LocationData;
import org.springframework.context.ApplicationEvent;

public class ParkingSpaceDataReceivedEvent extends ApplicationEvent {

    private LocationData data;

    public ParkingSpaceDataReceivedEvent(Object source, LocationData data) {
        super(source);
        this.data = data;
    }
    public LocationData getData() {
        return data;
    }

}
