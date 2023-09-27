/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Author:          Alexander Stein
 * Description:     Geo Location Search Service
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.api;

import de.fhdo.puls.shared_library.security.RoleDeclaration;
import de.fhdo.seelab.puls.parkingspace_search_service.dtos.LocationDataDto;
import de.fhdo.seelab.puls.parkingspace_search_service.services.LocationDataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@CrossOrigin(origins = "${api.cors.origins}", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping(value = "/resources/${api.version}/locationdatainterface", produces = "application/json")
public class MapSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapSearchController.class);

    private LocationDataService locationDataService;

    public MapSearchController() {}

    @PostConstruct
    private void init() { }

    @Autowired
    public void setLocationDataService(LocationDataService locationDataService) {
        this.locationDataService = locationDataService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/locationData")
    public LocationDataDto savePatientData(@RequestBody LocationDataDto locationDataDto) {
        LOGGER.info("Method (POST): saveLocationData");
        return locationDataService.save(locationDataDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/locationData")
    public LocationDataDto updateQuestionnaireData(@RequestBody LocationDataDto locationDataDto) {
        LOGGER.info("Method (PUT): updateLocationData");
        return locationDataService.update(locationDataDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData/{locationDataId}")
    public LocationDataDto getLocationData(@PathVariable("locationDataId") String locationDataId) {
        LOGGER.info("Method (GET): getLocationData");
        return locationDataService.getById(locationDataId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData")
    public List<LocationDataDto> getAllLocationData() {
        LOGGER.info("Method (GET): getAllLocationData");
        return locationDataService.getAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData/findByAddress")
    public List<LocationDataDto> getAllLocationDataByAddressLike(
            @RequestParam(name = "q") String addressSearchString) {
        LOGGER.info("Method (GET): getAllByAddressLike");
        return locationDataService.getAllByAddressLike(addressSearchString);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData/findByAddressFiltered")
    public List<LocationDataDto> getAllLocationDataByAddressLikeAndExtFilterLike(
            @RequestParam(name = "q") String addressSearchString, @RequestParam(name = "filter") String filter) {
        LOGGER.info("Method (GET): getAllByAddressLikeAndFilterLike");
        return locationDataService.getAllByAddressLikeAndExtFilterLike(addressSearchString, filter);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData/findWithin")
    public List<LocationDataDto> getAllLocationDataWithin(
            @RequestParam(name = "lat") String lat,
            @RequestParam(name = "lng") String lng,
            @RequestParam(name = "radius") double radius,
            @RequestParam(name = "metric") String metric) {
        LOGGER.info("Method (GET): getAllLocationDataWithin");
        return locationDataService.getAllWithin(
                Double.valueOf(lat),
                Double.valueOf(lng),
                radius,
                metric
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData/findNear")
    public List<LocationDataDto> getAllLocationDataNear(
            @RequestParam(name = "lat") String lat,
            @RequestParam(name = "lng") String lng,
            @RequestParam(name = "distance") double distance,
            @RequestParam(name = "metric") String metric) {
        LOGGER.info("Method (GET): getAllLocationDataNear");
        return locationDataService.getAllNear(
                Double.valueOf(lat),
                Double.valueOf(lng),
                distance,
                metric
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData/findNearFiltered")
    public List<LocationDataDto> getAllLocationDataNearAndExtFilter(
            @RequestParam(name = "lat") String lat,
            @RequestParam(name = "lng") String lng,
            @RequestParam(name = "distance") double distance,
            @RequestParam(name = "metric") String metric,
            @RequestParam(name = "filter") String filter
            ) {
        LOGGER.info("Method (GET): getAllLocationDataNearAndFilterLike");
        return locationDataService.getAllNearAndExtFilterLike(
                Double.valueOf(lat),
                Double.valueOf(lng),
                distance,
                metric,
                filter
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/locationData/exists/{locationDataId}")
    public boolean existsPatientData(@PathVariable("locationDataId") String locationDataId) {
        LOGGER.info("Method (GET): existsLocationData");
        return locationDataService.existsById(locationDataId);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/locationData/{locationDataId}")
    public void deletePatientData(@PathVariable("locationDataId") String locationDataId) {
        LOGGER.info("Method (DELETE): deleteLocationData");
        locationDataService.deleteById(locationDataId);
    }
}