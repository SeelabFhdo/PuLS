/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     User Profile Service
 * Author:          Alexander Stein
 * Dev. Date:       December 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.user_profile_service.services;

import de.fhdo.seelab.puls.user_profile_service.dtos.UserFavoriteLocationDto;

import java.util.List;

public interface UserFavoriteLocationService {

    UserFavoriteLocationDto save(
            UserFavoriteLocationDto patientDataDto
    );

    UserFavoriteLocationDto update(
            UserFavoriteLocationDto patientDataDto
    );

    UserFavoriteLocationDto getById(
            String patientDataId
    );

    List<UserFavoriteLocationDto> getAll();

    boolean existsById(String patientDataId);
}
