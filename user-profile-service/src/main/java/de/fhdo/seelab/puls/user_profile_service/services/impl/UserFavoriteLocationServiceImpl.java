/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     User Profile Service
 * Author:          Alexander Stein
 * Dev. Date:       December 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.user_profile_service.services.impl;

import de.fhdo.seelab.puls.user_profile_service.data.UserFavoriteLocation;
import de.fhdo.seelab.puls.user_profile_service.dtos.UserFavoriteLocationDto;
import de.fhdo.seelab.puls.user_profile_service.repositories.UserFavoriteLocationRepository;
import de.fhdo.seelab.puls.user_profile_service.services.UserFavoriteLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userFavoriteLocationService")
public class UserFavoriteLocationServiceImpl implements UserFavoriteLocationService {

    private UserFavoriteLocationRepository userFavoriteLocationRepository;

    @Autowired
    public void setLocationDataRepository(UserFavoriteLocationRepository locationDataRepository) {
        this.userFavoriteLocationRepository = locationDataRepository;
    }

    @Override
    public UserFavoriteLocationDto save(UserFavoriteLocationDto locationDataDto) {
        UserFavoriteLocation locationData = new UserFavoriteLocation();
        locationData.fromDto(locationDataDto);
        locationData.setId(null);

        locationData.setId(this.userFavoriteLocationRepository.save(locationData).getId());

        return locationData.toDto();
    }

    @Override
    public UserFavoriteLocationDto update(UserFavoriteLocationDto patientDataDto) {
        UserFavoriteLocation locationData = new UserFavoriteLocation();
        locationData.fromDto(patientDataDto);

        if (existsById(locationData.getId())) {
            locationData.setId(this.userFavoriteLocationRepository.save(locationData).getId());
        }

        return locationData.toDto();
    }

    @Override
    public UserFavoriteLocationDto getById(String locationDataId) {
        UserFavoriteLocationDto dto = null;
        Optional<UserFavoriteLocation> fetchedData = this.userFavoriteLocationRepository.findById(locationDataId);

        if (fetchedData.isPresent()) {
            dto = fetchedData.get().toDto();
        }

        return dto;
    }

    @Override
    public List<UserFavoriteLocationDto> getAll() {
        List<UserFavoriteLocation> fetchedData = this.userFavoriteLocationRepository.findAll();
        return fetchedData.stream().map(UserFavoriteLocation::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String locationDataId) {
        return this.userFavoriteLocationRepository.existsById(locationDataId);
    }
}
