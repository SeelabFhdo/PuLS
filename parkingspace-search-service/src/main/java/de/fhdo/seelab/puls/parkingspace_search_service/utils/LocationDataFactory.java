package de.fhdo.seelab.puls.parkingspace_search_service.utils;

import de.fhdo.puls.park_and_charge_service.common.event.ElectrifiedParkingSpaceCreatedEvent;
import de.fhdo.puls.park_and_charge_service.common.event.ParkingSpaceCreatedEvent;
import de.fhdo.seelab.puls.parkingspace_search_service.data.LocationData;

public class LocationDataFactory {

    private static LocationData newLocationData(String refId, String extFilter, double lat, double lng) {
        return new LocationData(refId, "", extFilter, String.valueOf(lat), String.valueOf(lng));
    }

    public static LocationData produceFrom(ParkingSpaceCreatedEvent event) {
        return newLocationData(
                event.getParkingSpaceId(),
                "ev-none",
                event.getLatitude(),
                event.getLongitude()
        );
    }

    public static LocationData produceFrom(ElectrifiedParkingSpaceCreatedEvent event) {
        return newLocationData(
                event.getParkingSpaceId(),
                "ev-electrified-" + event.getPluginType(),
                event.getLatitude(),
                event.getLongitude()
        );
    }

}
