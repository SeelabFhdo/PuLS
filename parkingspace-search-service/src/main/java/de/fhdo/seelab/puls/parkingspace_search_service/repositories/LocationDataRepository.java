/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.repositories;

import de.fhdo.seelab.puls.parkingspace_search_service.data.LocationData;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationDataRepository extends MongoRepository<LocationData, String> {

    List<LocationData> findAllByAddressLike(String addressSearchString);
    List<LocationData> findByLocationWithin(Circle c);
    List<LocationData> findByLocationNear(Point p, Distance d);

    List<LocationData> findAllByAddressLikeAndExtFilterLike(String addressSearchString, String extFilter);
    List<LocationData> findByLocationNearAndExtFilterLike(Point p, Distance d, String extFilter);
}
