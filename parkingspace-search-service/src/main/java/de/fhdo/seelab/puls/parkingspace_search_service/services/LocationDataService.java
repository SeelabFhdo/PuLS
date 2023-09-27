/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.services;

import de.fhdo.seelab.puls.parkingspace_search_service.dtos.LocationDataDto;

import java.util.List;

public interface LocationDataService {

    LocationDataDto save(
            LocationDataDto patientDataDto
    );

    LocationDataDto update(
            LocationDataDto patientDataDto
    );

    LocationDataDto getById(
            String patientDataId
    );

    List<LocationDataDto> getAll();

    List<LocationDataDto> getAllByAddressLike(
            String addressSearchString
    );

    List<LocationDataDto> getAllByAddressLikeAndExtFilterLike(
            String addressSearchString,
            String extFilter
    );

    List<LocationDataDto> getAllWithin(
            double lat,
            double lng,
            double radius,
            String metric
    );

    List<LocationDataDto> getAllNear(
            double lat,
            double lng,
            double distance,
            String metric
    );

    List<LocationDataDto> getAllNearAndExtFilterLike(
            double lat,
            double lng,
            double distance,
            String metric,
            String extFilter
    );

    void deleteById(
            String patientDataId
    );

    boolean existsById(
            String patientDataId
    );
}
