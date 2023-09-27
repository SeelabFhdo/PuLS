/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     User Profile Service
 * Author:          Alexander Stein
 * Dev. Date:       December 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.user_profile_service.repositories;

import de.fhdo.seelab.puls.user_profile_service.data.UserFavoriteLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteLocationRepository extends MongoRepository<UserFavoriteLocation, String> {

}
