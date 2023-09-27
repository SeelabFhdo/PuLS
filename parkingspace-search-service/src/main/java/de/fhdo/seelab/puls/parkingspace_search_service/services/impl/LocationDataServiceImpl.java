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

import de.fhdo.seelab.puls.parkingspace_search_service.dtos.LocationDataDto;
import de.fhdo.seelab.puls.parkingspace_search_service.repositories.LocationDataRepository;
import de.fhdo.seelab.puls.parkingspace_search_service.services.LocationDataService;
import de.fhdo.seelab.puls.parkingspace_search_service.data.LocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service(value = "locationDataService")
public class LocationDataServiceImpl implements LocationDataService {

    private LocationDataRepository locationDataRepository;

    @Autowired
    public void setLocationDataRepository(LocationDataRepository locationDataRepository) {
        this.locationDataRepository = locationDataRepository;
    }

    @Override
    public LocationDataDto save(LocationDataDto locationDataDto) {
        LocationData locationData = new LocationData();
        locationData.fromDto(locationDataDto);
        locationData.setId(null);

        locationData.setId(this.locationDataRepository.save(locationData).getId());

        return locationData.toDto();
    }

    @Override
    public LocationDataDto update(LocationDataDto patientDataDto) {
        LocationData locationData = new LocationData();
        locationData.fromDto(patientDataDto);

        if (existsById(locationData.getId())) {
            locationData.setId(this.locationDataRepository.save(locationData).getId());
        }

        return locationData.toDto();
    }

    @Override
    public LocationDataDto getById(String locationDataId) {
        LocationDataDto dto = null;
        Optional<LocationData> fetchedData = this.locationDataRepository.findById(locationDataId);

        if (fetchedData.isPresent()) {
            dto = fetchedData.get().toDto();
        }

        return dto;
    }

    @Override
    public List<LocationDataDto> getAll() {
        List<LocationData> fetchedData = this.locationDataRepository.findAll();
        return fetchedData.stream().map(LocationData::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LocationDataDto> getAllByAddressLike(String addressSearchString) {
        List<LocationData> fetchedData = this.locationDataRepository.findAllByAddressLike(addressSearchString);
        return fetchedData.stream().map(LocationData::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LocationDataDto> getAllByAddressLikeAndExtFilterLike(String addressSearchString, String extFilter) {
        List<LocationData> fetchedData = this.locationDataRepository.findAllByAddressLikeAndExtFilterLike(
                addressSearchString, extFilter);
        return fetchedData.stream().map(LocationData::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LocationDataDto> getAllWithin(double lat, double lng, double radius, String metric) {
        List<LocationData> fetchedData = this.locationDataRepository.findByLocationWithin(
                new Circle(new Point(lng, lat), new Distance(radius, Metrics.valueOf(metric))));
        return fetchedData.stream().map(LocationData::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LocationDataDto> getAllNear(double lat, double lng, double distance, String metric) {
        List<LocationData> fetchedData = this.locationDataRepository.findByLocationNear(
                new Point(lng, lat), new Distance(distance, Metrics.valueOf(metric)));
        return fetchedData.stream().map(LocationData::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LocationDataDto> getAllNearAndExtFilterLike(
            double lat, double lng, double distance, String metric, String extFilter) {
        List<LocationData> fetchedData = this.locationDataRepository.findByLocationNearAndExtFilterLike(
                new Point(lng, lat), new Distance(distance, Metrics.valueOf(metric)), extFilter);
        return fetchedData.stream().map(LocationData::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String locationDataId) {
        this.locationDataRepository.deleteById(locationDataId);
    }

    @Override
    public boolean existsById(String locationDataId) {
        return this.locationDataRepository.existsById(locationDataId);
    }
}
